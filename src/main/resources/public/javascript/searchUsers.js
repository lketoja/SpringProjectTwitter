
//--------- SEARCHING USERS --------------------
const allUsers = document.querySelector(".all-users");
const search = document.querySelector(".search-input");

const filterTodos = term => {
    Array.from(allUsers.children)
        .filter(user => user.textContent.toLowerCase().includes(term))
        .forEach(user => user.classList.remove('filtered'));

    Array.from(allUsers.children)
        .filter(user => !user.textContent.toLowerCase().includes(term))
        .forEach(user => user.classList.add('filtered'));
};

search.addEventListener('keyup', () => {
    const term = search.value.trim().toLowerCase();
    filterTodos(term);
});

//---------- RESIZING GALLERY-ADD-PHOTOS  -------------
const galleryAdd = document.getElementById('gallery-add');

const galleryAddColumn = document.querySelector('.gallery-add-column')

const changeHeight = () => {
    let columnWidth = galleryAddColumn.offsetWidth
    console.log(columnWidth)
    Array.from(galleryAdd.children)
        .forEach(div => {
            div.firstElementChild.style.width = 0.8 * columnWidth + 'px'
            div.firstElementChild.style.height = 0.8 * columnWidth + 'px'
        })
}

window.onload = changeHeight;

window.onresize = changeHeight;