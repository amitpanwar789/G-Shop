import axios from "axios";

const options = {
  method: "GET",
  url: "https://asos2.p.rapidapi.com/products/v2/list",
  params: {
    store: "US",
    offset: "0",
    categoryId: "4209",
    limit: "20",
    country: "US",
    sort: "freshness",
    currency: "USD",
    sizeSchema: "US",
    lang: "en-US",
  },
  headers: {
    "X-RapidAPI-Key": "bdefde9334mshc6b512cc2afd02bp1d8e40jsn542507a0b360",
    "X-RapidAPI-Host": "asos2.p.rapidapi.com",
  },
};

async function seeder() {
  const options = {
    method: "GET",
    url: "https://asos2.p.rapidapi.com/products/v2/list",
    params: {
      store: "US",
      offset: "0",
      categoryId: "4209",
      limit: "20",
      country: "US",
      sort: "freshness",
      currency: "USD",
      sizeSchema: "US",
      lang: "en-US",
    },
    headers: {
      "X-RapidAPI-Key": "bdefde9334mshc6b512cc2afd02bp1d8e40jsn542507a0b360",
      "X-RapidAPI-Host": "asos2.p.rapidapi.com",
    },
  };
  const { data } = await axios.request(options);
  const products = data.products;
  const userInfo =
    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYzOWQ3NmRjZjdhNjM4YWVlODhhODM2MCIsImlhdCI6MTY3Mjg5OTA4MiwiZXhwIjoxNjc1NDkxMDgyfQ.J37hEmj3WpwYiYm8PQc_EhZl5ojupympKzAuHKNjLLI";
  const config = {
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${userInfo}`,
    },
  };

  for (let i = 0; i < products.length; i++) {
    const product = {
      name: products[i].name,
      image: `https://${products[i].imageUrl}`,
      brand: products[i].brandName,
      price: products[i].price.current.value,
    };
    console.log(product)
    await axios.post(`http://localhost:5000/api/products`, product, config);
  }
}
seeder();
