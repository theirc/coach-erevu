const fs = require('fs');

const quizFolder = './quizzes/';

function cleanString (input) {
  return input
    .replace(/\s\s+/g, ' ')
    .replace(/\s+\?/g, '?')
    .replace(/\?\s+/g, '?')
    .replace(/\s+$/g, '')
    .replace(/“/g, '"')
    .replace(/”/g, '"')
    .replace(/’/g, '\'')
    ;
}

let jsonFilesWritten = 0;

fs.readdir(quizFolder, (err, files) => {
  let quizFilesToParse = files.filter(file => {
    return (file.substr(file.length - 4, 4) === '.txt');
  });

  quizFilesToParse.forEach(file => {
    
    fs.readFile(quizFolder + file, 'utf8', function(err, fileData) {

      const lines = fileData.split('\n');

      const title = cleanString(lines.shift());
      const questions = [];

      lines.shift();

      while (lines.length > 0) {
        let question = cleanString(lines.shift());

        let answers = [
          lines.shift(),
          lines.shift(),
          lines.shift()
        ];

        let correctAnswer = -1;
        answers = answers.map((answer, index) => {
          let newAnswer = answer.replace('[CORRECT]', '');
          if (answer.length !== newAnswer.length) {
            correctAnswer = index + 1;
            return newAnswer;
          }
          return answer;
        });

        answers = answers.map(answer => {
          return cleanString(answer);
        });

        lines.shift();

        question = question.replace(/^\d+\.\s*/, '');
        question = question.replace('(hitamwo inyishu imwe ibereye)', '');
        question = question.replace('(hitamwo inyishu imwe)', '');
        question = question.replace('(hitamwo kimwe)', '');
        question = question.replace('’', '\'');
        question = question.replace('“', '"');
        question = question.replace('”', '"');

        if (question.indexOf('?') !== question.length - 1 && question.charCodeAt(question.length-1) == 32) {
          question = question.substr(0, question.length -1);
        }

        let entry = {
          question: question,
          answers: answers,
          'answer-type': 'multiple-choice',
        };

        if (correctAnswer > -1) {
          entry['correct-answer'] = correctAnswer;
        }

        questions.push(entry);
      }

      const outputFilename = quizFolder + file + '.json';

      const quiz = {
        id: 0,
        title: title,
        color: '#45B39D',
        video: '',
        questions: questions
      };

      fs.writeFile(outputFilename, JSON.stringify(quiz, null, 2), 'utf8', err => {
        console.log('Wrote ' + outputFilename);

        if (++jsonFilesWritten === quizFilesToParse.length) {
          fs.readFile(quizFolder + 'content-skeleton.json', 'utf8', (err, data) => {
            const skeletonContentJSON = JSON.parse(data);

            skeletonContentJSON.categories.forEach(category => {
              const categoryColor = category.color;

              category.topics = category.topics.map((topic, index) => {
                const video = topic.video;
                const jsonFilename = topic.json;
                const json = JSON.parse(fs.readFileSync(quizFolder + jsonFilename, 'utf8'));

                json.id = index;
                json.color = categoryColor;
                json.video = video;

                return json;
              });
            });

            fs.writeFile(quizFolder + 'content.json', JSON.stringify(skeletonContentJSON, null, 2), 'utf8', err => {
              console.log('Wrote content.json');
            });
          });
        }
      });

    });

  });
});
