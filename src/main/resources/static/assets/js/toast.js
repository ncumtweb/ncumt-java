function showToast(message, type = 'success') {
    // Define styles and icons for different message types
    const toastTypes = {
        success: {
            className: 'alert-success',
            icon: 'bi-check-circle-fill'
        },
        error: {
            className: 'alert-danger',
            icon: 'bi-x-circle-fill'
        },
        warning: {
            className: 'alert-warning',
            icon: 'bi-exclamation-triangle-fill'
        },
        info: {
            className: 'alert-info',
            icon: 'bi-info-circle-fill'
        }
    };

    // Get the specific type settings, default to info if type is unknown
    const toastConfig = toastTypes[type] || toastTypes.info;

    // Create the element
    const toast = document.createElement('div');
    const timeout = 1500;
    toast.className = `alert ${toastConfig.className} d-flex align-items-center`;
    toast.setAttribute('role', 'alert');
    toast.style.cssText = 'position: fixed; top: 20px; right: 20px; z-index: 1050; box-shadow: 0 0.5rem 1rem rgba(0,0,0,.15); transition: opacity 0.5s ease-out; opacity: 1;';

    const icon = document.createElement('i');
    icon.className = `bi ${toastConfig.icon} me-2`;

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
    }, timeout + 500); // delay + fade duration
}
