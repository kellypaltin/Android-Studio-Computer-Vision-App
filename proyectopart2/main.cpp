#include <iostream>
#include <fstream>
#include <vector>
#include <opencv2/opencv.hpp>
#include <zlib.h>
#include <arpa/inet.h> 

using namespace std;
using namespace cv;

void readMNIST(const string& imageFile, const string& labelFile, vector<Mat>& images, vector<int>& labels) {
    gzFile imageFileStream = gzopen(imageFile.c_str(), "rb");
    gzFile labelFileStream = gzopen(labelFile.c_str(), "rb");

    if (!imageFileStream || !labelFileStream) {
        cerr << "Error al abrir los archivos" << endl;
        return;
    }

    int magicNumber, numImages, numLabels, numRows, numCols;

    gzread(imageFileStream, &magicNumber, sizeof(magicNumber));
    magicNumber = ntohl(magicNumber);

    if (magicNumber != 2051) {
        cerr << "Número mágico incorrecto en archivo de imágenes" << endl;
        return;
    }

    gzread(imageFileStream, &numImages, sizeof(numImages));
    numImages = ntohl(numImages);

    gzread(imageFileStream, &numRows, sizeof(numRows));
    numRows = ntohl(numRows);

    gzread(imageFileStream, &numCols, sizeof(numCols));
    numCols = ntohl(numCols);

    gzread(labelFileStream, &magicNumber, sizeof(magicNumber));
    magicNumber = ntohl(magicNumber);

    if (magicNumber != 2049) {
        cerr << "Número mágico incorrecto en archivo de etiquetas" << endl;
        return;
    }

    gzread(labelFileStream, &numLabels, sizeof(numLabels));
    numLabels = ntohl(numLabels);

    if (numImages != numLabels) {
        cerr << "Número de imágenes y etiquetas no coinciden" << endl;
        return;
    }

    images.resize(numImages);
    labels.resize(numImages);

    for (int i = 0; i < numImages; ++i) {
        Mat img(numRows, numCols, CV_8U);
        gzread(imageFileStream, img.data, numRows * numCols);
        images[i] = img;

        unsigned char label;
        gzread(labelFileStream, &label, 1);
        labels[i] = label;
    }

    gzclose(imageFileStream);
    gzclose(labelFileStream);
}

void extractHOGDescriptors(const vector<Mat>& images, vector<vector<float>>& hogDescriptors) {
    HOGDescriptor hog(
        Size(28, 28), 
        Size(14, 14), 
        Size(7, 7),   
        Size(14, 14), 
        9);           

    for (const auto& img : images) {
        vector<float> descriptors;
        hog.compute(img, descriptors);
        hogDescriptors.push_back(descriptors);
    }
}

void saveDescriptorsToCSV(const string& filename, const vector<vector<float>>& hogDescriptors, const vector<int>& labels) {
    ofstream file(filename);

    for (size_t i = 0; i < hogDescriptors.size(); ++i) {
        file << labels[i];
        for (const auto& val : hogDescriptors[i]) {
            file << "," << val;
        }
        file << endl;
    }

    file.close();
}

int main() {
    vector<Mat> trainImages;
    vector<int> trainLabels;
    vector<vector<float>> hogDescriptors;

    readMNIST("train-images-idx3-ubyte.gz", "train-labels-idx1-ubyte.gz", trainImages, trainLabels);
    extractHOGDescriptors(trainImages, hogDescriptors);
    saveDescriptorsToCSV("hog_train.csv", hogDescriptors, trainLabels);

    return 0;
}
