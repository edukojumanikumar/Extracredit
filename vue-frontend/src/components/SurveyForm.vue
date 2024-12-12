<template>
  <div>
    <h1>GMU Student Survey Form1234</h1>
    <form @submit.prevent="submitSurvey">
      <!-- Basic information fields -->
      <div>
        <label for="firstName">First Name:</label>
        <input v-model="formData.firstName" type="text" id="firstName" required />
      </div>
      <div>
        <label for="lastName">Last Name:</label>
        <input v-model="formData.lastName" type="text" id="lastName" required />
      </div>
      <div>
        <label for="streetAddress">Street Address:</label>
        <input v-model="formData.streetAddress" type="text" id="streetAddress" required />
      </div>
      <div>
        <label for="city">City:</label>
        <input v-model="formData.city" type="text" id="city" required />
      </div>
      <div>
        <label for="state">State:</label>
        <input v-model="formData.state" type="text" id="state" required />
      </div>
      <div>
        <label for="zip">Zip Code:</label>
        <input v-model="formData.zip" type="text" id="zip" required />
      </div>
      <div>
        <label for="telephone">Telephone Number:</label>
        <input v-model="formData.telephone" type="tel" id="telephone" required />
      </div>
      <div>
        <label for="email">Email:</label>
        <input v-model="formData.email" type="email" id="email" required />
      </div>
      <div>
        <label for="date">Date of Survey:</label>
        <input v-model="formData.date" type="date" id="date" required />
      </div>

      <!-- Checkbox for what students liked most about the campus -->
      <div>
        <label>What did you like most about the campus?</label>
        <div v-for="option in campusOptions" :key="option">
          <input
            type="checkbox"
            :id="option"
            :value="option"
            v-model="formData.likesMost"
          />
          <label :for="option">{{ option }}</label>
        </div>
      </div>

      <!-- Radio buttons for how students became interested -->
      <div>
        <label>How did you become interested in the university?</label>
        <div v-for="option in interestOptions" :key="option">
          <input
            type="radio"
            :id="option"
            :value="option"
            v-model="formData.interestSource"
          />
          <label :for="option">{{ option }}</label>
        </div>
      </div>

      <!-- Dropdown for likelihood of recommending the school -->
      <div>
        <label for="recommend">Likelihood of recommending the school:</label>
        <select v-model="formData.recommend" id="recommend" required>
          <option value="Very Likely">Very Likely</option>
          <option value="Likely">Likely</option>
          <option value="Unlikely">Unlikely</option>
        </select>
      </div>

      <!-- Text area for additional comments -->
      <div>
        <label for="comments">Additional Comments:</label>
        <textarea v-model="formData.comments" id="comments"></textarea>
      </div>

      <!-- Text box for Raffle -->
      <div>
        <label for="raffle">Raffle (enter at least 10 numbers from 1-100, separated by commas):</label>
        <input v-model="formData.raffle" type="text" id="raffle" required />
      </div>

      <!-- Submit and cancel buttons -->
      <button type="submit">Submit Survey</button>
      <button type="button" @click="clearForm">Cancel</button>
    </form>

    <button @click="fetchSurveys">Fetch Surveys</button>
    <div v-if="surveys.length > 0">
      <h2>Survey List</h2>
      <ul>
        <li v-for="survey in surveys" :key="survey.id">
          {{ survey.firstName }} {{ survey.lastName }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import SurveyService from '../services/SurveyService.js'; // Import the service

export default {
  data() {
    return {
      formData: {
        firstName: '',
        lastName: '',
        streetAddress: '',
        city: '',
        state: '',
        zip: '',
        telephone: '',
        email: '',
        date: '',
        likesMost: [], // Array for checkboxes
        interestSource: '', // Selected value for radio buttons
        recommend: '', // Dropdown selection
        raffle: '',
        comments: '',
      },
      surveys: [],
      campusOptions: ['students', 'location', 'campus', 'atmosphere', 'dorm rooms', 'sports'],
      interestOptions: ['friends', 'television', 'Internet', 'other'],
    };
  },
  methods: {
    async submitSurvey() {
      try {
        const response = await SurveyService.submitSurvey(this.formData);
        alert('Survey submitted successfully');
        console.log(response);
        this.clearForm(); // Clear form after submission
      } catch (error) {
        console.error('Error submitting survey:', error);
        alert('Failed to submit survey');
      }
    },
    async fetchSurveys() {
      try {
        const response = await SurveyService.getSurveyData();
        this.surveys = response;
      } catch (error) {
        console.error('Error fetching surveys:', error);
      }
    },
    clearForm() {
      this.formData = {
        firstName: '',
        lastName: '',
        streetAddress: '',
        city: '',
        state: '',
        zip: '',
        telephone: '',
        email: '',
        date: '',
        likesMost: [],
        interestSource: '',
        recommend: '',
        raffle: '',
        comments: '',
      };
    },
  },
  mounted() {
    this.fetchSurveys(); // Optionally, fetch surveys when the component is mounted
  },
};
</script>

<style scoped>
/* General form styling */
form {
  display: flex;
  flex-direction: column;
  width: 80%;
  margin: auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #f9f9f9;
}

/* Styling for form fields */
label {
  margin: 10px 0 5px;
  font-weight: bold;
}

input[type="text"],
input[type="email"],
input[type="tel"],
input[type="date"],
textarea,
select {
  width: 100%;
  padding: 8px;
  margin-bottom: 15px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 14px;
}

/* Styling for checkboxes and radio buttons */
input[type="checkbox"],
input[type="radio"] {
  margin-right: 5px;
}

/* Styling for buttons */
button {
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  padding: 10px 15px;
  font-size: 14px;
  cursor: pointer;
  margin-top: 10px;
}

button:hover {
  background-color: #45a049;
}

/* Additional styling for cancel button */
button[type="button"] {
  background-color: #f44336;
}

button[type="button"]:hover {
  background-color: #d32f2f;
}

/* Styling for the additional comments text area */
textarea {
  resize: vertical;
}

/* Styling for the heading */
h1 {
  text-align: center;
  color: #333;
  margin-bottom: 20px;
}

/* Styling for survey list */
ul {
  list-style-type: none;
  padding: 0;
}

li {
  padding: 5px 0;
  border-bottom: 1px solid #ddd;
}
</style>
