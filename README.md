# Project-Android-Studio-App-Computer-Vision
Development of a Computer Vision System to Detect/Classify Objects Employing Deep Learning and Feature Extraction.

## Problem Description - Part 1
Real-time image and video manipulation and fusion has become an indispensable tool for a wide range of applications, from interactive multimedia content creation to advanced surveillance and augmented reality experiences. However, integrating these functionalities into a mobile application presents a number of technical challenges that must be approached with care.


Among the main challenges are:

- Capture high quality images and videos: The application must be able to capture high resolution images or videos (sequence of images for a given time), minimizing latency and impact on device performance.

- Efficient on-device pre-processing: The pre-processing that should be applied to the captured images and videos should be performed using the OpenCv embedded library with C++ on the mobile device using Andriod Studio, thus taking advantage of and optimizing the use of available resources.

- Optimized data transmission: The transmission of preprocessed data to the remote server must be smooth and efficient, minimizing the required bandwidth and ensuring low latency for a real-time experience.

- Synchronization with the remote server: The mobile application must synchronize accurately with the remote server to ensure that the fusion of images and videos is performed correctly and without errors.

- High-quality image and video fusion: The remote server must have efficient algorithms that allow high-quality image and video fusion.


To address these challenges, we propose the development of a mobile application and a basic web application to perform the process of merging two sources of images or videos. The mobile application will be responsible for the capture, preprocessing and transmission of the data, while the web application will be responsible for the final fusion and presentation of the result.

![image](https://github.com/user-attachments/assets/5ebff9ab-c4e3-4f84-9052-b0b1d22d4c79)

# Mobile Application & Web Server
![image](https://github.com/user-attachments/assets/cc55928b-2e0c-43e3-baca-54878cedbc80)

![image](https://github.com/user-attachments/assets/c499bb23-40d5-492b-9892-c0d7ca922b4b)

![image](https://github.com/user-attachments/assets/7ed27a4e-f9be-4a0b-b2c7-44a213544374)

![image](https://github.com/user-attachments/assets/11250d87-9135-4d8c-8a51-c5d0ef4e9afa)

![image](https://github.com/user-attachments/assets/dbe3dd4a-25cd-4b37-ad13-847ba63fb842)

![image](https://github.com/user-attachments/assets/7b011738-046f-4a6e-8dbc-c5d86790efb4)

![image](https://github.com/user-attachments/assets/d523d75e-8174-4fca-9d14-4200ccf59810)

# Problem Description - Part 2
Based on the application developed in Part I, we incorporated two new functionalities listed below:
▪ Face detection: Program a code that allows to perform face detection on the mobile device (C++ library) using Haar Cascades or a similar approach, so that it can detect not only the area where the face is, but also eyes, nose and mouth. Once the face is detected, the captured images or video with the face, eyes, nose and mouth positions should be sent to the web server. The web server will have to apply an effect on the parts of the face, for example: place lenses or glasses, change the color of the eyes, etc. To do this, you must define what effect you want to apply and design how the effect will be implemented
▪ Object classification using Histograms of Oriented Gradients (HOG): An object classifier should be trained using the HOG descriptor and a Multilayer Perceptron Neural Network or similar. To do this, you must select a corpus of images on which the classification will be performed, extract the HOG descriptor and then train the neural network, where the HOG is used to classify handwritten digits (MNIST corpus). Once the neural network is trained, it must implement the classifier in the web server, so that when a new image is loaded, the prediction is performed.

To face the proposed challenges, we started based on this explanatory diagram which outlines the main process that will be found in our Application:
![image](https://github.com/user-attachments/assets/6bb5e8ec-79d2-472b-8728-4add0797a55f)

# Mobile Application & Web Server
![image](https://github.com/user-attachments/assets/089d5775-ac0b-41cb-8aaa-47b015e8de05)

![image](https://github.com/user-attachments/assets/b69bc52c-8e1b-4747-837e-5f97b5d048be)

![image](https://github.com/user-attachments/assets/6f6fdf43-39e3-49f9-ad25-46d20379f575)

![image](https://github.com/user-attachments/assets/3092bfce-568b-493d-ae3a-342e96289cbb)

