const uploadLabel = document.getElementById("uploadLabel");
const uploadInput = document.getElementById("uploadInput");
const uploadData = document.getElementById("uploadData");
const popUp = document.getElementById("darkBackground");
const img = document.getElementById("imageToEdit");
const box = document.getElementById("editBox");

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

uploadLabel.addEventListener("drop", function(e){
    e.preventDefault();
    uploadInput.files = e.dataTransfer.files;
    uploadImage();
});