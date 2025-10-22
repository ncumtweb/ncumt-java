import {sendErrorToBackend} from './logService.js';

ClassicEditor.create(document.querySelector("#content"), {
    toolbar: ['undo', 'redo', '|', 'heading',
        '|', 'bold', 'italic', 'link', 'numberedList', 'bulletedList']
}).catch(error => {
    sendErrorToBackend(error);
});
