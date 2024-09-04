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

![image](https://github.com/user-attachments/assets/7a67e1de-5157-4ba0-aacd-a774eab78a17)

# Mobile Application & Web Server
![image](https://github.com/user-attachments/assets/f2e9cd64-6229-46ab-b9fb-f2e17bffd17c)

![image](https://github.com/user-attachments/assets/e210123c-274f-493a-b43e-f4753a85e466)

![image](https://github.com/user-attachments/assets/7623d7fe-e068-4c6e-8527-ace64765d7eb)

![image](https://github.com/user-attachments/assets/8e11b06e-c944-487a-84c2-0932adfa928c)

![image](https://github.com/user-attachments/assets/9015f4ae-5ff0-453a-8fa5-ec60877f8c57)

![image](https://github.com/user-attachments/assets/dfb03c0b-66c9-4c8d-810d-cbd30aafe6ce)

![image](https://github.com/user-attachments/assets/0a1d2851-6e25-45d2-a5ff-9281af76df43)

# Problem Description - Part 2
Based on the application developed in Part I, we incorporated two new functionalities listed below:
▪ Face detection: Program a code that allows to perform face detection on the mobile device (C++ library) using Haar Cascades or a similar approach, so that it can detect not only the area where the face is, but also eyes, nose and mouth. Once the face is detected, the captured images or video with the face, eyes, nose and mouth positions should be sent to the web server. The web server will have to apply an effect on the parts of the face, for example: place lenses or glasses, change the color of the eyes, etc. To do this, you must define what effect you want to apply and design how the effect will be implemented
▪ Object classification using Histograms of Oriented Gradients (HOG): An object classifier should be trained using the HOG descriptor and a Multilayer Perceptron Neural Network or similar. To do this, you must select a corpus of images on which the classification will be performed, extract the HOG descriptor and then train the neural network, where the HOG is used to classify handwritten digits (MNIST corpus). Once the neural network is trained, it must implement the classifier in the web server, so that when a new image is loaded, the prediction is performed.

To face the proposed challenges, we started based on this explanatory diagram which outlines the main process that will be found in our Application:
![image](https://github.com/user-attachments/assets/63e27152-730c-4e36-9045-a96dbd3596f8)

# Mobile Application & Web Server
![image](https://github.com/user-attachments/assets/d782ee12-898f-451f-a028-8b0d0fdf0b05)

![image](https://github.com/user-attachments/assets/436f9905-9885-4ce0-8805-28e2a114b5ef)

![image](https://github.com/user-attachments/assets/30342943-1abf-4665-8d11-68dcf16f224e)

![image](https://github.com/user-attachments/assets/9d64c586-880e-43bb-a116-491bf77b438a)

