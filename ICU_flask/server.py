from flask import Flask, render_template, Response, jsonify, request
from camera import VideoCamera, RecordingThread
from flask_mysqldb import MySQL
from flask_cors import CORS

import time
import pymysql

app = Flask(__name__)

video_camera = None
global_frame = None
status = 'true'
time_list = None

userId = None
examNumber = None
conn = pymysql.connect(host='localhost', user='minsu',
                       password='haessae0', db='icu', charset='utf8')


@app.route('/<inputusername>/<inputtestnum>')
def index(inputusername, inputtestnum):

    # 쿼리문 실행
    global conn

    cur = conn.cursor()
    cur.execute("SELECT * FROM quiz where exam_num = %s", inputtestnum)

    global userId
    global examNumber
    userId = inputusername
    examNumber = inputtestnum

    fetchdata = cur.fetchall()
    print(len(fetchdata))
    quizSelection_list = []
    index = 0
    for problem in fetchdata:

        quizSelection_list.append([])
        quiz_list = problem[4].split('/')
        quizSelection_list[index].append(problem[3])
        quizSelection_list[index].append(problem[2])
        quizSelection_list[index].append(quiz_list[0])
        quizSelection_list[index].append(quiz_list[1])
        quizSelection_list[index].append(quiz_list[2])
        quizSelection_list[index].append(quiz_list[3])
        index += 1

    cur.execute(
        "SELECT open_time, close_time FROM exam WHERE exam_num=%s", examNumber)
    fetchdata2 = cur.fetchall()
    openTime = fetchdata2[0][0]
    closeTime = fetchdata2[0][1]

    return render_template('index.html', data_list=quizSelection_list, user_name=userId, exam_num=examNumber, open_time=openTime, close_time=closeTime)


@app.route('/record_status', methods=['POST'])
def record_status():
    global video_camera
    global time_list
    global userId
    global examNumber
    global conn

    if video_camera != None:
        json = request.get_json()
        global status
        status = json['status']

        curs = conn.cursor()

        if status == "false":
            video_camera.stop_record()

            try:
                if time_list != None and userId != None and examNumber != None:
                    tmpstr = ''

                    for time in time_list:
                        tmpstr += time+'/'

                    if tmpstr == '':
                        sql = "UPDATE quizforstudent SET cheating_time=%s, lier=%s where stu_id=%s and exam_num=%s"
                        curs.execute(sql, ('', 'False', userId, examNumber))
                    else:
                        sql = "UPDATE quizforstudent SET cheating_time=%s, lier=%s where stu_id=%s and exam_num=%s"
                        curs.execute(sql, (tmpstr, 'True', userId, examNumber))

                    conn.commit()
                    conn.close()
            except Exception:
                print(Exception)
                conn.rollback()
                conn.close()
            return jsonify(result="finished")


def video_stream():
    global video_camera
    global global_frame
    start_time = time.time()
    global time_list
    time_list = list()

    if video_camera == None:
        video_camera = VideoCamera(start_time, time_list)

    video_camera.start_record()

    while True:
        frame = video_camera.get_frame()
        if frame != None:
            global_frame = frame
            yield (b'--frame\r\n'
                   b'Content-Type: image/jpeg\r\n\r\n' + frame + b'\r\n\r\n')
        else:
            yield (b'--frame\r\n'
                   b'Content-Type: image/jpeg\r\n\r\n' + global_frame + b'\r\n\r\n')


@app.route('/video_viewer')
def video_viewer():
    return Response(video_stream(),
                    mimetype='multipart/x-mixed-replace; boundary=frame')


if __name__ == '__main__':
    app.run(debug=True, threaded=True)
