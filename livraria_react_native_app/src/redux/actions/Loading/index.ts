import { LoadingPayload, LOADING_END, LOADING_START } from "./actionLoadingTypes"

export function startLoading({ isActivityIndicator, isBlockScreen }: LoadingPayload) {
  return (dispatch: any) => {
    return dispatch({ type: LOADING_START, payload: { isActivityIndicator, isBlockScreen } });
  }
}

export function endLoading() {
  return (dispatch: any) => {
    return dispatch({ type: LOADING_END });
  }
}
