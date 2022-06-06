import os
from re import A
import cv2
import threading
from flask import Flask, render_template, Response
import tensorflow as tf
import numpy as np
from yolo_helper import YoloV3, load_darknet_weights, draw_outputs
from gaze_tracking import GazeTracking
import time
import sys
import pymysql

yolo = YoloV3()
gaze = GazeTracking()

load_darknet_weights(yolo, 'yolov3.weights')


class RecordingThread (threading.Thread):
    def __init__(self, name, camera):
        threading.Thread.__init__(self)
        self.name = name
        self.isRunning = True
        self.cap = camera
        fourcc = cv2.VideoWriter_fourcc(*'avc1')
        self.out = cv2.VideoWriter(
            './static/video.mp4', fourcc, 20.0, (640, 480))

    def run(self):
        while self.isRunning:
            ret, frame = self.cap.read()
            if ret:
                self.out.write(frame)
        self.out.release()

    def stop(self):
        self.isRunning = False

    def __del__(self):
        self.out.release()


class VideoCamera(object):
    def __init__(self, start_time, time_list):
        self.cap = cv2.VideoCapture(0, cv2.CAP_DSHOW)
        self.is_record = False
        self.out = None
        self.recordingThread = None
        self.start_time = start_time
        self.timelist = time_list

    def __del__(self):
        self.cap.release()

    def get_frame(self):
        doubt_eye = 0
        while(True):
            ret, frame = self.cap.read()
            if ret == False:
                break

            gaze.refresh(frame)
            frame = gaze.annotated_frame()
            text = ""

            if gaze.is_blinking():
                text = " "
            elif gaze.is_right():
                text = "Looking right"
                doubt_eye = doubt_eye+1
            elif gaze.is_left():
                text = "Looking left"
                doubt_eye = doubt_eye+1
            elif gaze.is_center():
                text = "Looking center"

            cv2.putText(frame, text, (90, 60),
                        cv2.FONT_HERSHEY_DUPLEX, 1.6, (147, 58, 31), 2)

            cv2.imshow("Demo", frame)

            if cv2.waitKey(1) == 27:
                break

            img = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
            img = cv2.resize(img, (320, 320))
            img = img.astype(np.float32)
            img = np.expand_dims(img, 0)
            img = img / 255

            class_names = [c.strip()
                           for c in open("models/classes.TXT").readlines()]
            boxes, scores, classes, nums = yolo(img)
            print("nums:", nums)
            count = 0

            if self.is_record:
                elapsed_time = time.time() - self.start_time
            for i in range(nums[0]):
                if int(classes[0][i] == 0 or classes[0][i] > 1):
                    count += 1

            if count > 1:
                self.timelist.append(time.strftime(
                    "%H:%M:%S", time.gmtime(elapsed_time)))
            elif doubt_eye > 0:
                self.timelist.append(time.strftime(
                    "%H:%M:%S", time.gmtime(elapsed_time)))

            frame = draw_outputs(
                frame, (boxes, scores, classes, nums), class_names)
            ret, buffer = cv2.imencode('.jpg', frame)
            frame = buffer.tobytes()
            return buffer.tobytes()
        else:
            return None

    def start_record(self):
        self.is_record = True
        self.recordingThread = RecordingThread(
            "Video Recording Thread", self.cap)
        self.recordingThread.start()

    def stop_record(self):
        self.is_record = False
        if self.recordingThread != None:
            self.recordingThread.stop()
