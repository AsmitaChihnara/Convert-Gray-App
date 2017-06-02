#include <jni.h>
#include <opencv2/opencv.hpp>
#include <iostream>
#include "com_example_asmita_convertgray_Convertgray.h"

using namespace cv;
using namespace std;

int toGray(Mat& img, Mat& gray);

JNIEXPORT jint JNICALL Java_com_example_asmita_convertgray_Convertgray_convertgray(JNIEnv *env, jobject obj,jlong addrRgba, jlong addrGray) {

    Mat &mRgb = *(Mat *) addrRgba;
    Mat &mGray = *(Mat *) addrGray;

    int conv;
    jint retVal;

    conv = toGray(mRgb, mGray);
    retVal = (jint) conv;

    return retVal;
}

int toGray(Mat& img, Mat& gray)
{
    cvtColor(img, gray, CV_RGBA2GRAY); // Assuming RGBA input

    if (gray.rows == img.rows && gray.cols == img.cols)
    {
        return (1);
    }
    return(0);
}

