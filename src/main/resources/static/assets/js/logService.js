// logService.js

/**
 * Sends frontend error details to the backend logging endpoint.
 * @param {Error} error - The error object caught from the frontend.
 */
export function sendErrorToBackend(error) {
    const errorDetails = {
        name: error.name,
        message: error.message,
        stack: error.stack,
    };

    // 將錯誤詳情轉換為 JSON 字串
    const errorBody = JSON.stringify(errorDetails);

    fetch('/frontend/log/error', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: errorBody
    }).then(response => {
        if (!response.ok) {
            console.error('Failed to send error to backend. Status: ' + response.status);
        }
    }).catch(backendError => {
        console.error('Error sending error to backend:', backendError);
    });
}
