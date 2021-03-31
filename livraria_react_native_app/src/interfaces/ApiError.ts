export default interface ApiError {
  apierror: {
    debugMessage: string,
    message: string
    status: string
    subErrors: Object
    timestamp: Date
  }
}