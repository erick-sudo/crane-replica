import os
import requests

# List of image URLs from the Kotlin code
image_urls = [
    "https://images.unsplash.com/photo-1534308983496-4fabb1a015ee?ixlib=rb-1.2.1&auto=format&fit=crop&w=250",
    "https://images.unsplash.com/photo-1495749388945-9d6e4e5b67b1?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=250",
    "https://images.unsplash.com/photo-1562625964-ffe9b2f617fc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=250&q=250",
    "https://images.unsplash.com/photo-1515443961218-a51367888e4b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=250",
    "https://images.unsplash.com/flagged/photo-1556202256-af2687079e51?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=250",
    "https://images.unsplash.com/photo-1542384557-0824d90731ee?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=250",
    "https://images.unsplash.com/photo-1567337710282-00832b415979?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=250",
    "https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=250",
    "https://images.unsplash.com/photo-1445019980597-93fa8acb246c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=250",
    "https://images.unsplash.com/photo-1570213489059-0aac6626cade?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=250",
    "https://images.unsplash.com/photo-1561409037-c7be81613c1f?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=250",
    "https://images.unsplash.com/photo-1455587734955-081b22074882?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=250",
    "https://images.unsplash.com/46/sh3y2u5PSaKq8c4LxB3B_submission-photo-4.jpg?ixlib=rb-1.2.1&auto=format&fit=crop&w=250",
    "https://images.unsplash.com/photo-1570214476695-19bd467e6f7a?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=250",
    "https://images.unsplash.com/photo-1544735716-392fe2489ffa?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=250",
    "https://images.unsplash.com/photo-1539037116277-4db20889f2d4?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=250",
    "https://images.unsplash.com/photo-1518548419970-58e3b4079ab2?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=250",
    "https://images.unsplash.com/photo-1515542622106-78bda8ba0e5b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=250",
    "https://images.unsplash.com/photo-1534423839368-1796a4dd1845?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=250",
    "https://images.unsplash.com/photo-1544550581-5f7ceaf7f992?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=250",
    "https://images.unsplash.com/photo-1557160854-e1e89fdd3286?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=250",
    "https://images.unsplash.com/photo-1562883676-8c7feb83f09b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=250",
    "https://images.unsplash.com/photo-1486575008575-27670acb58db?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=250",
    "https://images.unsplash.com/photo-1534308983496-4fabb1a015ee?ixlib=rb-1.2.1&auto=format&fit=crop&w=250",
]

# Create a directory to save the images
os.makedirs('images', exist_ok=True)

# Function to download an image from a URL
def download_image(url):
    try:
        response = requests.get(url)
        response.raise_for_status()  # Check if the request was successful
        filename = os.path.join('images', os.path.basename(url.split('?')[0]))
        with open(filename, 'wb') as f:
            f.write(response.content)
        print(f"Downloaded {filename}")
    except requests.exceptions.RequestException as e:
        print(f"Failed to download {url}: {e}")

# Download all images
for url in image_urls:
    download_image(url)