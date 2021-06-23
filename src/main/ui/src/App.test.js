const faker = require('faker');
const puppeteer = require('puppeteer');

// Create global variables to be used in the beforeAll function
let browser;
let page;

// Create a random data for testing
const random = {
    username: faker.name.firstName(),
    password: faker.internet.password(),
    location: faker.address.city()
};

const appUrlBase = 'http://localhost:3000'

beforeAll(async () => {
    // launch browser
    browser = await puppeteer.launch({
        headless: false,
        devtools: false,
        slowMo: 100
    });

    // creates a new page in the opened browser
    page = await browser.newPage()

    // wait until page loads
    await page.goto(appUrlBase);
    await page.waitForSelector('.login-form');
});

const input = {
    username: '#formBasicEmail',
    password: '#formBasicPassword',
    newLocationName: 'body > div.fade.modal.show > div > div'
};

const button = {
    signup: '#root > div > form > form > div.form-row > button:nth-child(2)',
    login: '#root > div > form > form > div.form-row > button:nth-child(1)',
    addNewLocation: '#root > div > div > div.add-button > button',
    saveNewLocation: 'body > div.fade.modal.show > div > div > div > form > div > div:nth-child(4) > button'
}

const map = '#root > div > div > div.map-container.mapboxgl-map';

describe('Map Application', () => {
    it('should sign up a new user', async () => {
        // fill input fields 
        await page.click(input.username);
        await page.type(input.username, random.username);
        await page.click(input.password);
        await page.type(input.password, random.password);

        // click signup button
        await page.click(button.signup);
    }, 9000000);

    it('should sign in a user', async () => {
        // click login button
        await page.click(button.login);
        await page.waitFor(9000);
    }, 9000000)

    it('should open save location dialog', async () => {
        // click add location button
        await page.click(button.addNewLocation);

        // click on map
        await page.click(map);

        // wait until save location dialog loads
        await page.waitForSelector('body > div.fade.modal.show > div > div');
    });

    it('should save new locations', async () => {
        // fill input fields
        await page.click(input.newLocationName);
        await page.type(input.newLocationName, random.location);

        // click save button
        await page.click(button.saveNewLocation);
    }, 9000000);
});

// Close the browser after tests
afterAll(() => {
    // browser.close()
});