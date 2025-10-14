image.onchange = evt => {
    const [file] = image.files;
    if (file) {
      preview_image.src = URL.createObjectURL(file);
      console.log(preview_image);
    }
}
