
function confirmDelete(text) {
    return confirm(text) || event.stopImmediatePropagation();
}
