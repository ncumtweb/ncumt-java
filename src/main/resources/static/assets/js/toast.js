function showToast(message) {
    // Create the element
    const toast = document.createElement('div');
    const timeout = 1500;
    toast.className = 'alert alert-success d-flex align-items-center';
    toast.setAttribute('role', 'alert');
    toast.style.cssText = 'position: fixed; top: 20px; right: 20px; z-index: 1050; box-shadow: 0 0.5rem 1rem rgba(0,0,0,.15); transition: opacity 0.5s ease-out; opacity: 1;';

    const icon = document.createElement('i');
    icon.className = 'bi bi-check-circle-fill me-2';

    const messageDiv = document.createElement('div');
    messageDiv.innerHTML = message; // Use innerHTML to allow for simple HTML in the message

    toast.appendChild(icon);
    toast.appendChild(messageDiv);

    document.body.appendChild(toast);

    // After a delay, start fading out
    setTimeout(() => {
        toast.style.opacity = '0';
    }, timeout);

    // After fade out, remove from DOM
    setTimeout(() => {
        if (toast) {
            toast.remove();
        }
    }, timeout + 500); // 3000ms delay + 500ms fade
}
