const sourceImage = document.getElementById('imageToEdit');

const canvas = document.getElementById('croppedCanvas');
const ctx = canvas.getContext('2d');

const csrfToken = document.querySelector('meta[name="_csrf"]').content;
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;



function cropAndSend() {
    const circleSize = parseFloat(getComputedStyle(document.documentElement).getPropertyValue('--circle-size'));

    var width = sourceImage.naturalWidth;
    var height = sourceImage.naturalHeight;
    var min = Math.min(width, height);
    var image = sourceImage.getBoundingClientRect();

    canvas.width = min;
    canvas.height = min;

    ctx.beginPath();
    ctx.arc(min/2, min/2, min/2, 0, Math.PI * 2);
    ctx.closePath();
    ctx.clip();

    var baseTop = image.x - min;
    var actualTop = canvas.getBoundingClientRect().x - min;
    console.log(baseTop);
    console.log(actualTop);

    ctx.drawImage(
        sourceImage, //image
        (width-min)/2 - offsetX*width/sourceImage.clientWidth, (height-min)/2 - offsetY*height/sourceImage.clientHeight, //where to cut
        min, min, //how much to cut
        0, 0, //where to paste in canvas
        min, min//resize
    );

    canvas.toBlob(function (blob) {
        const reader = new FileReader();
        reader.onload = function () {
            const arrayBuffer = reader.result;
            const byteArray = new Uint8Array(arrayBuffer);
            sendToBackend(byteArray);
        };
        reader.readAsArrayBuffer(blob);
    }, 'image/png');
}

function sendToBackend(byteArray) {
    const formData = new FormData();
    formData.append('title', "holis");
    formData.append('description', "desc :3");
    formData.append('type', "type");
    formData.append('image', new Blob([byteArray], { type: 'application/octet-stream' }));

    fetch('/subirpublicacion', {
        method: 'POST',
        body: formData,
        headers: {
            [csrfHeader]: csrfToken,
        },
    })
        .then(response => response.json())
        .then(data => console.log("Backend Response:", data))
        .catch(error => console.error("Error:", error));
}
