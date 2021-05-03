import { LOADING_END, LOADING_START } from "./actionLoadingTypes"

export function startLoading() {
  return (dispatch: any) => {
    return dispatch({type: LOADING_START});
  }
}

export function endLoading() {
  return (dispatch: any) => {
    return dispatch({type: LOADING_END});
  }
}
