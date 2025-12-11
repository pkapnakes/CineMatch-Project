document.addEventListener("DOMContentLoaded", function () {
    const params = new URLSearchParams(window.location.search);
    const query = params.get("query");

    fetch(`/api/search?query=${query}`)
        .then(response => response.json())
        .then(movies => {
            const container = document.getElementById("resultsContainer");

            container.innerHTML = "";

            movies.forEach(movie => {
                const poster = movie.posterUrl
                    ? `https://image.tmdb.org/t/p/w500${movie.posterUrl}`
                    : "/images/no-poster.png";  // fallback placeholder

                const movieCard = `
        <a href="/movie/${movie.id}" class="search-card">
            <img src="${poster}" alt="${movie.title}">
            <p>${movie.title}</p>
        </a>
    `;

                container.innerHTML += movieCard;
            });

        });
});
