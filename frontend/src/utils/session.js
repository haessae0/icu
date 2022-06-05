import jwt_decode from "jwt-decode";

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
