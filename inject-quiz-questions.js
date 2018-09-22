const fs = require('fs');

const quizFolder = './quizzes/';
const contentFilename = './app/src/main/res/raw/content.json';

function getContentJSON () {
  return new Promise((res, rej) => {
    fs.readFile(contentFilename, 'utf8', (err, contentFileData) => {
      res(JSON.parse(contentFileData));
    });
  });
}

function getQuizJSON (path) {
  return new Promise((res, rej) => {
    fs.readFile(path, 'utf8', function(err, fileData) {
      res(JSON.parse(fileData));
    });
  });
}

getContentJSON().then(contentJSON => {
  fs.readdir(quizFolder, (err, files) => {
    let quizFilesToParse = files.filter(file => {
      return (file.substr(file.length - 5, 5) === '.json'
          && file !== 'content.json'
          && file !== 'content-skeleton.json');
    });

    Promise.all(quizFilesToParse.map(file => {
      return getQuizJSON(quizFolder + file)
    })).then(injectionJSONs => {
      injectionJSONs.forEach(injectionJSON => {
        contentJSON.categories.forEach(category => {
          category.topics.forEach(topic => {
            if (topic.title === injectionJSON.title) {
              topic.questions = injectionJSON.questions;
            }
          });
        });
      });
      fs.writeFile(contentFilename, JSON.stringify(contentJSON, null, 2), 'utf8', (err) => {
        console.log('Done');
      });
    });
  });
});

