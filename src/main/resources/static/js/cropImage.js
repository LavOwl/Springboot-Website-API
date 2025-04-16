const sourceImage = document.getElementById('imageToEdit');
const sizeInputt = document.getElementById("sizeInput");


const canvas = document.getElementById('croppedCanvas');
const ctx = canvas.getContext('2d');

const csrfToken = document.querySelector('meta[name="_csrf"]').content;
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

var byteArray = null;


function cropAndSend() {
    var width = sourceImage.naturalWidth;
    var height = sourceImage.naturalHeight;
    var min = Math.min(width, height)/(1+sizeInputt.value/100);

    canvas.width = min;
    canvas.height = min;

    ctx.beginPath();
    ctx.arc(min/2, min/2, min/2, 0, Math.PI * 2);
    ctx.closePath();
    ctx.clip();

    ctx.drawImage(
        sourceImage, //image
        (width-min)/2 - offsetX*width/sourceImage.clientWidth, (height-min)/2 - offsetY*height/sourceImage.clientHeight, //where to cut
        min, min, //how much to cut
        0, 0, //where to paste in canvas
        min, min//resize
    );

    let url = canvas.toDataURL("image/png");
    canvas.toBlob(function(blob){
        byteArray = blob;
    }, "image/png");
    uploadLabel.style.backgroundImage = `url(${url})`;
}

document.getElementById('uploadForm').addEventListener('submit', function(event) {
    event.preventDefault();
    const formData = new FormData(); //Create Form object to send

    let formElements = document.getElementById('uploadForm').elements; //List all form elements

    for (let i = 0; i < formElements.length; i++) {
        let element = formElements[i];
        
        if (element.type === "submit" || element.type === "file") continue; //Skips the submit button and the img input

        formData.append(element.name, element.value); //Appends the element to FormData
    }
    if(byteArray != null){
        formData.append("image", new Blob([byteArray]));
    }

    fetch('/actualizarPerfil', {
        method: 'POST',
        body: formData,
        headers: {
            [csrfHeader]: csrfToken,
        },
    })
        .then(response => response.json())
        .then(data => console.log("Backend Response:", data))
        .catch(error => console.error("Error:", error));
});