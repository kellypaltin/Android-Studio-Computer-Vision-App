import cv2
from flask import Flask, render_template, request
import numpy as np
from responses.response_json import response_json
from routes.files import routes_files
import os
from moviepy.editor import VideoFileClip

app = Flask(__name__)
app.register_blueprint(routes_files)

@app.route("/")
def main():
    filter_param = request.args.get('filter', 'original')
    
    if filter_param == 'original':
        video_filename = 'video/Camara/Camara.mp4'
    elif filter_param == 'filtro1':
        video_filename = 'video/FiltroOne/FiltroOne.mp4'
    elif filter_param == 'filtro2':
        video_filename = 'video/FiltroTwo/FiltroTwo.mp4'
    elif filter_param == 'filtro3':
        video_filename = 'video/FiltroThree/FiltroThree.mp4'
    elif filter_param == 'filtro4':
        video_filename = 'video/FiltroFour/FiltroFour.mp4'
    elif filter_param == 'originalCaptura':
        video_filename = 'VideosCaptura/Video0.mp4'
    elif filter_param == 'filtro1Captura':
        video_filename = 'VideosCaptura/Video1.mp4'
    elif filter_param == 'filtro2Captura':
        video_filename = 'VideosCaptura/Video2.mp4'
    elif filter_param == 'filtro3Captura':
        video_filename = 'VideosCaptura/Video3.mp4'
    elif filter_param == 'filtro4Captura':
        video_filename = 'VideosCaptura/Video4.mp4'
    else:
        video_filename = 'video/Camara/Camara.mp4'
    
    video_exists = True
    return render_template('index.html', video_filename=video_filename, video_exists=video_exists)

@app.route('/capture')
def capture():
    cap = cv2.VideoCapture(0)

    ret, frame = cap.read()
    if not ret:
        return "Error: No se puede capturar la imagen"

    cv2.imwrite(r'C:\Users\USUARIO_PC\Desktop\server-files-flask-master\files\captura.jpg', frame)

    cap.release()
    videos()
    return response_json("Foto capturada y guardada como 'captured_image.jpg'")

def videos():
    background = cv2.imread(r"C:\Users\USUARIO_PC\Desktop\server-files-flask-master\files\captura.jpg")
    videos = [r"C:\Users\USUARIO_PC\Desktop\server-files-flask-master\static\video\Camara\Camara.avi",
              r"C:\Users\USUARIO_PC\Desktop\server-files-flask-master\static\video\FiltroOne\FiltroOne.avi",
              r"C:\Users\USUARIO_PC\Desktop\server-files-flask-master\static\video\FiltroTwo\FiltroTwo.avi",
              r"C:\Users\USUARIO_PC\Desktop\server-files-flask-master\static\video\FiltroThree\FiltroThree.avi",
              r"C:\Users\USUARIO_PC\Desktop\server-files-flask-master\static\video\FiltroFour\FiltroFour.avi"]
    
    for i, video_path in enumerate(videos):
        cap = cv2.VideoCapture(video_path)
        
        frame_width = int(cap.get(cv2.CAP_PROP_FRAME_WIDTH))
        frame_height = int(cap.get(cv2.CAP_PROP_FRAME_HEIGHT))
        fps = int(cap.get(cv2.CAP_PROP_FPS))
        
        video_filename = f"Video{i}.avi"
        fourcc = cv2.VideoWriter_fourcc(*'XVID')
        name_path = r"C:\Users\USUARIO_PC\Desktop\server-files-flask-master\static\VideosCaptura"
        video_path = os.path.join(name_path, video_filename)
        
        out = cv2.VideoWriter(video_path, fourcc, fps, (frame_width, frame_height))

        while True:
            ret, img = cap.read()
            if not ret:
                break
            
            background_resized = cv2.resize(background, (img.shape[1], img.shape[0]))
            gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
            blurred = cv2.GaussianBlur(gray, (5, 5), 0)
            binary = cv2.adaptiveThreshold(blurred, 255, cv2.ADAPTIVE_THRESH_GAUSSIAN_C, 
                                           cv2.THRESH_BINARY_INV, 11, 2)
            contours, hierarchy = cv2.findContours(binary, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
            mask = np.zeros(img.shape[:2], dtype=np.uint8)
            
            for contour in contours:
                cv2.drawContours(mask, [contour], -1, (255), thickness=cv2.FILLED)
            
            mask = cv2.medianBlur(mask, 9)
            extracted = cv2.bitwise_and(img, img, mask=mask)
            mask_inv = cv2.bitwise_not(mask)
            background_masked = cv2.bitwise_and(background_resized, background_resized, mask=mask_inv)
            result = cv2.add(background_masked, extracted)
            
            out.write(result)

        cap.release()
        out.release()
        
        convertir_avi_a_mp4(f"{name_path}\\Video{i}.avi", f"{name_path}\\Video{i}.mp4")

def convertir_avi_a_mp4(ruta_avi, ruta_mp4):
    video = VideoFileClip(ruta_avi)
    video.write_videofile(ruta_mp4)

if __name__ == '__main__':
    app.run(debug=True, port="4000", host="0.0.0.0")
