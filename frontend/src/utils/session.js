import jwt_decode from "jwt-decode";

//user_id (username) 반환
function getUserIdFromSession() {
  const str = sessionStorage.getItem("Authorization");
  if (str != null) {
    const arr = str.split(" ");
    const token = arr[1];
    let decoded = jwt_decode(token);
    return decoded.sub;
  } else {
    return null;
  }
}

//user_ROLE 반환
function getUserRoleFromSession() {
  const str = sessionStorage.getItem("Authorization");
  if (str != null) {
    const arr = str.split(" ");
    const token = arr[1];
    let decoded = jwt_decode(token);
    return decoded.auth;
  } else {
    return null;
  }
}

// decoded.auth  추가 구현 예정

//arr[0] // Bearer
//arr[1] // eyJjb20iOiJFRFVDQVJFIiwidHlwIjoiSldUIiwiYWxnIjoiSFM1MTIifQ.eyJzdWIiOiJhYWFAbmF2ZXIuY29tIiwiYXV0aCI6IlJPTEVfU1RVREVOVCIsImV4cCI6MTYxODMxOTEwNX0.qZ7cmn3tIDtoHpja_ynSDyOerW9-HG1SzXALxpEdJ9-KY-QNhJ_CqSvSu3HDWQ_sL7Jap87sK1WhwUqlCOcEFQ

export { getUserIdFromSession, getUserRoleFromSession };
