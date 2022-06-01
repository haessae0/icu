import { tests } from "./index";

function fetchTest(examNumber) {
  return tests.get(examNumber);
}

function deleteTest(examNumber) {
  return tests.delete(examNumber);
}
export { fetchTest, deleteTest };
