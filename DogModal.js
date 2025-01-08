import React from 'react';

const DogModal = ({ images, breed, onClose }) => {
  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <h2>{breed} Pictures</h2>
        <div className="images">
          {images.map((image, index) => (
            <img key={index} src={image} alt={`${breed}`} />
          ))}
        </div>
        <button className="close-button" onClick={onClose}>
          Close
        </button>
      </div>
    </div>
  );
};

export default DogModal;
