//Mocks an image with passed size
export function createTestImage(size: number): File {
    let image = new File([""], "test.png");
    Object.defineProperty(image, "size", {value: size, writable: false});
    return image;
}