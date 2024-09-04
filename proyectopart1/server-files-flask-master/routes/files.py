from flask import Blueprint, request
from os import getcwd, path, remove
from responses.response_json import response_json
from moviepy.editor import VideoFileClip
import shutil
import os
import cv2
import re

routes_files = Blueprint("routes_files", __name__)

@routes_files.post("/upload")
def upload_file():
    try:
        name = request.args.get('name') 
        file = request.files['file']
        dir_path = os.path.join(getcwd(), "files", name)
        os.makedirs(dir_path, exist_ok=True)
        file.save(os.path.join(dir_path, file.filename))
        return response_json("success")
    except FileNotFoundError:
        return response_json("Folder not found", 404)

@routes_files.get("/createVideo")
def createVideo():
    try:
        name = request.args.get('name')
        dir_path = fr"C:\Users\USUARIO_PC\Desktop\server-files-flask-master\files\{name}"
        
        os.makedirs(dir_path, exist_ok=True)

        archivos = sorted(os.listdir(dir_path))
        archivos.sort(key=extract_number)
        img_array = []
        
        for archivo in archivos:
            dirA = os.path.join(dir_path, archivo)
            img = cv2.imread(dirA)
            img_array.append(img)

        if len(img_array) == 0:
            return response_json("No images found in the folder", 404)

        height, width, _ = img_array[0].shape
        video_filename = f"{name}.avi"
        name_path = fr"C:\Users\USUARIO_PC\Desktop\server-files-flask-master\static\video\{name}"
        os.makedirs(name_path, exist_ok=True)
        video_path = os.path.join(name_path, video_filename)
        video = cv2.VideoWriter(video_path, cv2.VideoWriter_fourcc(*'XVID'), 20, (width, height))

        for img in img_array:
            video.write(img)
        video.release()

        convertir_avi_a_mp4(f"{name_path}\\{name}.avi", f"{name_path}\\{name}.mp4")

        return response_json("success")
    except FileNotFoundError:
        return response_json("Folder not found", 404)
    except Exception as e:
        # Captura otras excepciones y devuelve un error genérico
        print(f"An error occurred: {str(e)}")
        return response_json("An error occurred", 500)

@routes_files.delete('/delete')
def delete_file():
    directory_path = r"C:\Users\USUARIO_PC\Desktop\server-files-flask-master\files"
    if not os.path.isdir(directory_path):
        return {"error": "No datos en la carpeta"}, 404
    try:
        for filename in os.listdir(directory_path):
            file_path = os.path.join(directory_path, filename)
            if os.path.isfile(file_path) or os.path.islink(file_path):
                os.unlink(file_path)
            elif os.path.isdir(file_path):
                shutil.rmtree(file_path)
        return {"message": "Los archivos fueron eliminados correctamente"}, 200
    except Exception as e:
        return {"error": str(e)}, 500
    
def extract_number(filename):
    match = re.search(r'\d+', filename)
    return int(match.group()) if match else 0

def convertir_avi_a_mp4(ruta_avi, ruta_mp4):
    video = VideoFileClip(ruta_avi)
    video.write_videofile(ruta_mp4)
