import jwt_decode from "jwt-decode";

//user_id (userId) 반환
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

export { getUserIdFromSession, getUserRoleFromSession };
