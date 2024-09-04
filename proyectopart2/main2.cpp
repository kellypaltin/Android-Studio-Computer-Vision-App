#include <iostream>
#include <vector>
#include <opencv2/opencv.hpp>
#include <tensorflow/lite/interpreter.h>
#include <tensorflow/lite/kernels/register.h>
#include <tensorflow/lite/model.h>
#include <tensorflow/lite/tools/gen_op_registration.h>

using namespace std;
using namespace cv;

vector<float> computeHOG(const Mat &image) {
    vector<float> features;
    HOGDescriptor hog(
        Size(28, 28), 
        Size(14, 14), 
        Size(7, 7),   
        Size(7, 7),   
        9            
    );
    hog.compute(image, features);
    return features;
}

int main() {
    const char* model_path = "/home/lubuntu/Documentos/proyectopart2/mlp_model.tflite";
    std::unique_ptr<tflite::FlatBufferModel> model = tflite::FlatBufferModel::BuildFromFile(model_path);
    if (!model) {
        std::cerr << "Failed to load model " << model_path << std::endl;
        return -1;
    }

    tflite::ops::builtin::BuiltinOpResolver resolver;
    std::unique_ptr<tflite::Interpreter> interpreter;
    tflite::InterpreterBuilder(*model, resolver)(&interpreter);
    if (!interpreter) {
        std::cerr << "Failed to construct interpreter" << std::endl;
        return -1;
    }

    if (interpreter->AllocateTensors() != kTfLiteOk) {
        std::cerr << "Failed to allocate tensors!" << std::endl;
        return -1;
    }

    float* input = interpreter->typed_input_tensor<float>(0);
    int input_size = interpreter->input_tensor(0)->bytes / sizeof(float);

    Mat img = imread("/home/lubuntu/Documentos/proyectopart2/numero3.jpg", IMREAD_GRAYSCALE);
    if (img.empty()) {
        cerr << "Error al cargar la imagen" << endl;
        return -1;
    }

    vector<float> features = computeHOG(img);
    if (features.size() != input_size) {
        cerr << "Error: El tamaño de las características HOG no coincide con el tamaño de entrada del modelo" << endl;
        return -1;
    }

    for (int i = 0; i < input_size; ++i) {
        input[i] = features[i];
    }

    if (interpreter->Invoke() != kTfLiteOk) {
        std::cerr << "Failed to invoke interpreter" << std::endl;
        return -1;
    }

    float* output = interpreter->typed_output_tensor<float>(0);
    int output_size = interpreter->output_tensor(0)->bytes / sizeof(float);

    int class_id = std::distance(output, std::max_element(output, output + output_size));
    cout << "Predicted class: " << class_id << endl;

    string text = "Predicted: " + to_string(class_id);
    putText(img, text, Point(10, 30), FONT_HERSHEY_SIMPLEX, 1, Scalar(255), 2);
    imshow("Prediction", img);
    waitKey(0);
    return 0;
}