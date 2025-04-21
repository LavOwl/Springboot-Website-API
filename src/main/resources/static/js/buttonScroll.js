
const scrollContainer = document.getElementById('list');
const leftBtn = document.querySelector('.scroll-btn.left');
const rightBtn = document.querySelector('.scroll-btn.right');
let isDragging = false;
let startX;

function scrollCards(direction) {
    const scrollAmount = 300;
    scrollContainer.scrollBy({
        left: direction * scrollAmount,
        behavior: 'smooth'
    });
}

function updateButtons() {
    const maxScroll = scrollContainer.scrollWidth - scrollContainer.clientWidth;
    console.log(scrollContainer.scrollWidth);
    console.log(scrollContainer.clientWidth);
    leftBtn.classList.toggle('hidden', scrollContainer.scrollLeft === 0);
    rightBtn.classList.toggle('hidden', scrollContainer.scrollLeft >= maxScroll - 1);
}

scrollContainer.addEventListener('scroll', updateButtons);
window.addEventListener('resize', updateButtons);
window.addEventListener('load', updateButtons);




scrollContainer.addEventListener('mousedown', function (e) {
    isDragging = true;
    e.preventDefault();
    startX = e.pageX - scrollContainer.offsetLeft;
    scrollLeft = scrollContainer.scrollLeft;
});

scrollContainer.addEventListener('mousemove', function (e) {
    if (isDragging) {
        e.preventDefault();
        scrollContainer.scrollLeft = scrollLeft -  e.pageX + scrollContainer.offsetLeft + startX;
    }
});

scrollContainer.addEventListener('mouseup', function () {
    isDragging = false;
});

scrollContainer.addEventListener('mouseleave', function () {
    isDragging = false;
});
  