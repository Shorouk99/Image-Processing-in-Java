import java.awt.Color;

public class KernelFilter {

    private static Picture kernel(Picture picture, double[][] weights) {
        int h = picture.height();
        int w = picture.width();
        int hk = Math.floorDiv(weights.length, 2);
        //   System.out.println(hk);
        Picture copy = new Picture(w, h);
        for (int r = 0; r < h; r++) {
            for (int c = 0; c < w; c++) {
                int sumR = 0;
                int sumG = 0;
                int sumB = 0;

                for (int i = 0; i < weights.length; i++) {
                    int row = r - (hk - i);
                    if (row < 0) row = row + (h);
                    else if (row > h - 1) row = row % (h);
                    //if (row > h - 1) row = 0;
                    //else if (row < 0) row = h - 1;
                    for (int j = 0; j < weights.length; j++) {
                        int col = c - (hk - j);
                        if (col < 0) col = col + (w);
                        else if (col > w - 1) col = col % (w);
                        //if (col > w - 1) col = 0;
                        //else if (col < 0) col = w - 1;
                        // System.out.println(col + "   " + col % (w - 1));
                        Color color = picture.get(col, row);
                        sumR += color.getRed() * weights[i][j];
                        sumB += color.getBlue() * weights[i][j];
                        sumG += color.getGreen() * weights[i][j];
                    }
                }
                sumR = sumR > 255 ? 255 : (sumR < 0 ? 0 : Math.round(sumR));
                sumG = sumG > 255 ? 255 : (sumG < 0 ? 0 : Math.round(sumG));
                sumB = sumB > 255 ? 255 : (sumB < 0 ? 0 : Math.round(sumB));
                Color n_color = new Color(sumR, sumG, sumB);
                copy.set(c, r, n_color);
            }
        }

        return copy;
    }

    public static Picture identity(Picture picture) {
        double[][] matrix = new double[3][3];
        matrix[1][1] = 1;
        // System.out.println(Arrays.deepToString(matrix));
        Picture filtered = kernel(picture, matrix);
        return filtered;
    }

    public static Picture gaussian(Picture picture) {
        double[][] matrix = new double[3][3];
        matrix[1][1] = 4.0 / 16.0;
        matrix[0][1] = matrix[1][0] = matrix[1][2] = matrix[2][1] = 2.0 / 16.0;
        matrix[0][0] = matrix[0][2] = matrix[2][0] = matrix[2][2] = 1.0 / 16.0;
        // System.out.println(Arrays.deepToString(matrix));
        Picture filtered = kernel(picture, matrix);
        return filtered;
    }

    public static Picture sharpen(Picture picture) {
        double[][] matrix = new double[3][3];
        matrix[1][1] = 5;
        matrix[0][1] = matrix[1][0] = matrix[1][2] = matrix[2][1] = -1;
        matrix[0][0] = matrix[0][2] = matrix[2][0] = matrix[2][2] = 0;
        //  System.out.println(Arrays.deepToString(matrix));
        Picture filtered = kernel(picture, matrix);
        return filtered;
    }

    public static Picture laplacian(Picture picture) {
        double[][] matrix = new double[][]{{-1, -1, -1}, {-1, 8, -1}, {-1, -1, -1}};
        // System.out.println(Arrays.deepToString(matrix));
        Picture filtered = kernel(picture, matrix);
        return filtered;
    }

    public static Picture emboss(Picture picture) {
        double[][] matrix = new double[][]{{-2, -1, 0}, {-1, 1, 1}, {0, 1, 2}};
        // System.out.println(Arrays.deepToString(matrix));
        Picture filtered = kernel(picture, matrix);
        return filtered;
    }

    public static Picture motionBlur(Picture picture) {
        double[][] matrix = new double[][]{
                {1 / 9.0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1 / 9.0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1 / 9.0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1 / 9.0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1 / 9.0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1 / 9.0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1 / 9.0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1 / 9.0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1 / 9.0},
        };
        //  System.out.println(Arrays.deepToString(matrix));
        Picture filtered = kernel(picture, matrix);
        return filtered;
    }

    public static void main(String[] args) {
        Picture picture = new Picture(args[0]);
        picture.show();
        Picture picture1 = identity(picture);
        picture1.show();
        Picture picture2 = gaussian(picture);
        picture2.show();
        Picture picture3 = sharpen(picture);
        picture3.show();
        Picture picture4 = laplacian(picture);
        picture4.show();
        Picture picture5 = emboss(picture);
        picture5.show();
        Picture picture6 = motionBlur(picture);
        picture6.show();
    }
}
