import axios from 'axios';

// Update the base URL to match your back-end server
//const SURVEY_API_BASE_URL = 'http://localhost:8080/api/surveys';
const SURVEY_API_BASE_URL = 'http://a6a99f9c46a6a40c9a9623ae0680cc06-1356326351.us-east-2.elb.amazonaws.com/api/surveys';

class SurveyService {
  submitSurvey(formData) {
    return axios.post(SURVEY_API_BASE_URL, formData)
      .then(response => {
        console.log('Survey Data Submitted: ', response.data);
        return response.data;
      })
      .catch(error => {
        console.error('Error submitting survey data: ', error);
        throw error;
      });
  }

  getSurveyData() {
    return axios.get(SURVEY_API_BASE_URL)
      .then(response => {
        return response.data;
      })
      .catch(error => {
        console.error('Error fetching survey data: ', error);
        throw error;
      });
  }
}

export default new SurveyService();
