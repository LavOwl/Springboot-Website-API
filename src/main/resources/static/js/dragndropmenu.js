const uploadLabel = document.getElementById("uploadLabel");
const uploadInput = document.getElementById("uploadInput");
const uploadData = document.getElementById("uploadData");
const popUp = document.getElementById("darkBackground");
const img = document.getElementById("imageToEdit");
const box = document.getElementById("editBox");
const sizeInput = document.getElementById("sizeInput");

uploadInput.addEventListener("change", uploadImage);

function uploadImage(){
    let imgSource = URL.createObjectURL(uploadInput.files[0]);
    popUp.style.display = "block";
    box.style.display = "flex block";
    img.src = imgSource;
    img.onload = () => {
        document.documentElement.style.setProperty("--circle-size", `${(Math.min(img.clientWidth, img.clientHeight)/2).toString()}px`);
    };
    uploadLabel.style.backgroundImage = `url(${imgSource})`;
    uploadData.textContent = "";
};

function closePopUp(){
    popUp.style.display = "none";
    box.style.display = "none";
}

uploadLabel.addEventListener("dragover", function(e){
    e.preventDefault();
});

sizeInput.addEventListener("input", () => {
    img.style.maxWidth = `${75 + 75/100 * sizeInput.value}%`;
    img.style.maxHeight = `${75 + 75/100 * sizeInput.value}%`;
});

uploadLabel.addEventListener("drop", function(e){
    e.preventDefault();
    uploadInput.files = e.dataTransfer.files;
    uploadImage();
});