import pymysql
import time

from flask import Flask, render_template, Response, jsonify, request
from flask_mysqldb import MySQL
from camera import VideoCamera,RecordingThread

# Flask 인스턴스 생성
app = Flask(__name__)

# 데이터베이스 접근(host,user,password,db는 사용자 환경에 맞게 설정할 것)
conn = pymysql.connect(host='localhost',user='root',password='root',db='icu',charset='utf8')

v_camera = None
g_frame = None
t_list = None
userid = None
examnum = None

status = 'true'

# @ : 장식자를 의미하며 URL 연결에 사용
@app.route('/<input_name>/<input_examnum>')
def index(input_name, input_examnum):
    global userid
    global examnum

    userid = input_name;
    examnum = input_examnum;

    # 쿼리문 실행
    global conn

    query = conn.curssor()
    query.execute("SELECT * FROM testproblem where exam_num = %s", input_examnum)

    # fetchall() : 레코드를 배열 형식으로 저장
    f_data = query.fetchall()
    print(len(f_data))
    p_list = []
    index = 0
    
    for problem in f_data:
      
        p_list = problem[5].split('/')

        p_list.append([])
        p_list[index].append(p_list[0]) # 문제보기 0
        p_list[index].append(p_list[1]) # 문제보기 1
        p_list[index].append(p_list[2]) # 문제보기 2
        p_list[index].append(p_list[3]) # 문제보기 3
        p_list[index].append(problem[2]) # 문제 및 답
        p_list[index].append(problem[4]) # 문제 및 답
        
        index+=1 # 그 다음 문제 출력

    # 쿼리 실행
    query.execute("SELECT open_time, close_time FROM test WHERE exam_num=%s",examnum)

    f_data2 = query.fetchall()
    openTime = f_data2[0][0] # 시험 시작 시간
    closeTime = f_data2[0][1] # 시험 끝 시간

    # html 랜더링
    return render_template('index.html', data_list = p_list, u_name = userid, exam_num = examnum, open_time = openTime, close_time = closeTime)


@app.route('/status', methods=['POST'])
def status():
    global v_camera 
    global t_list
    global userid
    global examnum
    global conn

    if v_camera != None:
        json = request.get_json()

        global status 
        status = json['status']
    
        querys = conn.cursor()

        if status == "false":
            v_camera.stop_record()
            
            try :
                if t_list !=None and userid != None and examnum != None:
                    tmp_str = ''
                    
                    for time in t_list:
                        tmp_str += time+'/'
                    
                    if tmp_str == '':
                        sql = "UPDATE studenttest SET cheating_time=%s, lier=%s where stu_id=%s and exam_num=%s" 
                        querys.execute(sql,('','False',userid,examnum))
                    else :
                        sql = "UPDATE studenttest SET cheating_time=%s, lier=%s where stu_id=%s and exam_num=%s"
                        querys.execute(sql,(tmp_str,'True',userid,examnum))
                    conn.commit()    
                    conn.close()
            except Exception:
                print(Exception)
                conn.rollback()    
                conn.close()
            return jsonify(result="Complete")

@app.route('/v_viwer')
def v_viwer():
    return Response(v_stream(),
                    mimetype='multipart/x-mixed-replace; boundary=frame')

def v_stream(): # 좌측 하단 규격
    global v_camera 
    global g_frame
    global t_list

    open_time = time.time()
    t_list = list()

    if v_camera == None:
        v_camera = VideoCamera(open_time,t_list)
    
    v_camera.start_record()

    while True:
        frame = v_camera.get_frame()
        if frame != None:
            g_frame = frame
            yield (b'--frame\r\n'
                    b'Content-Type: image/jpeg\r\n\r\n' + frame + b'\r\n\r\n')
        else:
            yield (b'--frame\r\n'
                            b'Content-Type: image/jpeg\r\n\r\n' + g_frame + b'\r\n\r\n')

# 메인 함수 선언
if __name__ == '__main__':
    app.run(debug=True, threaded=True)