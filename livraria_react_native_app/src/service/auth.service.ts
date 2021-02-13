interface Response {
  token: string,
  user: {
    email: string,
    name: string
  }
}

const SingIn = (): Promise<Response> => {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve({
        token: 'lkadlfkjglfiagfbcgbfuyaviucfwbcfycgcfiagcfuiygodsjvbs',
        user: {
          name: 'Herculano',
          email: 'herculanoleo@gmail.com',
        },
      });
    }, 2000);
  });
};

export {SingIn};
