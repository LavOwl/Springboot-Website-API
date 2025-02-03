const uploadLabel = document.getElementById("uploadLabel");
const uploadInput = document.getElementById("uploadInput");
const uploadData = document.getElementById("uploadData");

uploadInput.addEventListener("change", uploadImage);

function uploadImage(){
    let imgSource = URL.createObjectURL(uploadInput.files[0]);
    uploadLabel.style.backgroundImage = `url(${imgSource})`;
    uploadData.textContent = "";
};

uploadLabel.addEventListener("dragover", function(e){
    e.preventDefault();
});

uploadLabel.addEventListener("drop", function(e){
    e.preventDefault();
    uploadInput.files = e.dataTransfer.files;
    uploadImage();
});