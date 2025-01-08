import React from 'react';

const DogList = ({ breeds, onBreedClick }) => {
  return (
    <ul className="dog-list">
      {Object.keys(breeds).map((breed) => (
        <React.Fragment key={breed}>
          <li className="breed" onClick={() => onBreedClick(breed)}>
            {breed}
          </li>
          {breeds[breed].map((subBreed) => (
            <li
              key={subBreed}
              className="sub-breed"
              onClick={() => onBreedClick(breed, subBreed)}
            >
              — {subBreed}
            </li>
          ))}
        </React.Fragment>
      ))}
    </ul>
  );
};

export default DogList;
