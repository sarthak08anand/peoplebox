 import React, { useState, useEffect } from "react";
import axios from "axios";
import DogList from "./DogList";
import DogModal from "./DogModal";

const App = () => {
  const [breeds, setBreeds] = useState({});
  const [selectedBreed, setSelectedBreed] = useState("");
  const [images, setImages] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);

  // Fetch all breeds and sub-breeds
  useEffect(() => {
    axios.get("https://dog.ceo/api/breeds/list/all")
      .then((response) => {
        setBreeds(response.data.message);
      })
      .catch((error) => {
        console.error("Error fetching breeds:", error);
      });
  }, []);

  // Fetch images for a selected breed/sub-breed
  const fetchImages = (breed) => {
    axios.get(`https://dog.ceo/api/breed/${breed}/images/random/4`)
      .then((response) => {
        setImages(response.data.message);
        setIsModalOpen(true);
      })
      .catch((error) => {
        console.error("Error fetching images:", error);
      });
  };

  // Handle breed/sub-breed click
  const handleBreedClick = (breed) => {
    setSelectedBreed(breed);
    fetchImages(breed);
  };

  // Close modal
  const closeModal = () => {
    setIsModalOpen(false);
    setImages([]);
  };

  return (
    <div className="App">
      <h1>Dog Breeds</h1>
      <DogList breeds={breeds} onBreedClick={handleBreedClick} />
      {isModalOpen && (
        <DogModal images={images} onClose={closeModal} />
      )}
    </div>
  );
};

export default App;