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
testNum = None
conn = pymysql.connect(host='localhost', user='root',
                       password='root', db='educare', charset='utf8')


@app.route('/<inputusername>/<inputtestnum>')
def index(inputusername, inputtestnum):

    # 쿼리문 실행
    global conn

    cur = conn.cursor()
    cur.execute("SELECT * FROM testproblem where test_num = %s", inputtestnum)

    global userId
    global testNum
    userId = inputusername
    testNum = inputtestnum

    fetchdata = cur.fetchall()
    print(len(fetchdata))
    prosel_list = []
    idx = 0
    for problem in fetchdata:

        prosel_list.append([])
        prob_list = problem[5].split('/')
        prosel_list[idx].append(problem[4])
        prosel_list[idx].append(problem[2])
        prosel_list[idx].append(prob_list[0])
        prosel_list[idx].append(prob_list[1])
        prosel_list[idx].append(prob_list[2])
        prosel_list[idx].append(prob_list[3])
        idx += 1

    cur.execute(
        "SELECT start_time, end_time FROM test WHERE test_num=%s", testNum)
    fetchdata2 = cur.fetchall()
    startTime = fetchdata2[0][0]
    endTime = fetchdata2[0][1]

    return render_template('index.html', data_list=prosel_list, user_name=userId, test_num=testNum, start_time=startTime, end_time=endTime)


@app.route('/record_status', methods=['POST'])
def record_status():
    global video_camera
    global time_list
    global userId
    global testNum
    global conn

    if video_camera != None:
        json = request.get_json()
        global status
        status = json['status']

        curs = conn.cursor()

        if status == "false":
            video_camera.stop_record()

            try:
                if time_list != None and userId != None and testNum != None:
                    tmpstr = ''

                    for time in time_list:
                        tmpstr += time+'/'

                    if tmpstr == '':
                        sql = "UPDATE studenttest SET cheat_time=%s, is_cheating=%s where stu_id=%s and test_num=%s"
                        curs.execute(sql, ('', 'False', userId, testNum))
                    else:
                        sql = "UPDATE studenttest SET cheat_time=%s, is_cheating=%s where stu_id=%s and test_num=%s"
                        curs.execute(sql, (tmpstr, 'True', userId, testNum))

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
