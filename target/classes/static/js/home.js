document.addEventListener("DOMContentLoaded", function () {

    const searchBtn = document.getElementById("searchBtn");
    const searchInput = document.getElementById("searchInput");

    // 🔍 Click Search Button
    searchBtn.addEventListener("click", performSearch);

    // 🔍 Press Enter = Search
    searchInput.addEventListener("keypress", (e) => {
        if (e.key === "Enter") {
            e.preventDefault();
            performSearch();
        }
    });

    function performSearch() {
        let query = searchInput.value.trim();
        if (query.length > 0) {
            window.location.href = "/search?query=" + encodeURIComponent(query);
        }
    }

    // Load Hero + Trending
    loadHomeContent();
});


// ===============================
// Load Trending Movies
// ===============================
function loadHomeContent() {
    fetch("/api/trending")
        .then(res => res.json())
        .then(data => {
            renderHero(data[0]);
            renderTrending(data);
        })
        .catch(err => console.error("Error loading trending:", err));
}


// ===============================
// Render Hero Section (with fallback)
// ===============================
function renderHero(movie) {
    let heroSection = document.querySelector(".hero-section");

    const bg = movie.backdropUrl
        ? movie.backdropUrl
        : (movie.posterUrl
            ? `https://image.tmdb.org/t/p/original${movie.posterUrl}`
            : "/images/no-poster-bg.jpg"); // fallback background!

    heroSection.style.backgroundImage = `url('${bg}')`;
    heroSection.style.backgroundSize = "cover";
    heroSection.style.backgroundPosition = "center";

    document.getElementById("hero-title").textContent = movie.title;
    document.getElementById("hero-overview").textContent = movie.overview;

    document.getElementById("hero-play").onclick = () => {
        window.location.href = "/movie/" + movie.id;
    };
}


// ===============================
// Render Trending Movies (with fallback)
// ===============================
function renderTrending(movies) {
    const container = document.querySelector(".trending-container");
    container.innerHTML = "";

    movies.forEach(m => {
        const poster = m.posterUrl
            ? `https://image.tmdb.org/t/p/w500${m.posterUrl}`
            : "/images/no-poster.png"; // fallback poster

        const card = document.createElement("div");
        card.classList.add("movie-card");

        card.innerHTML = `
            <a href="/movie/${m.id}">
                <img src="${poster}" alt="${m.title}">
                <p>${m.title}</p>
            </a>
        `;

        container.appendChild(card);
    });
}
