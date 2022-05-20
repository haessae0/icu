var buttonStop = document.getElementById("stop");

buttonStop.disabled = false;


buttonStop.onclick = function() {
    //buttonRecord.disabled = false;
    // buttonStop.disabled = true;  
    // XMLHttpRequest
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // alert(xhr.responseText);
            // enable download link
            var downloadLink = document.getElementById("download");
            downloadLink.text = "Download Video";
            downloadLink.href = "/static/video.mp4";
        }
    }
    xhr.open("POST", "/record_status");
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhr.send(JSON.stringify({ status: "false" }));
};