#include <jni.h>
#include <opencv2/opencv.hpp>
#include <opencv2/stitching/stitcher.hpp>

using namespace std;
using namespace cv;

extern "C" {
JNIEXPORT void JNICALL Java_edu_pdx_cs_vision_stitcher_NativeStitcherWrapper_NativeStitch(JNIEnv* env, jobject thisObj, jobjectArray jInputArray, jlong jResultMat, jboolean waveCorrect, jboolean multiBand);

JNIEXPORT void JNICALL Java_edu_pdx_cs_vision_stitcher_NativeStitcherWrapper_NativeStitch(JNIEnv* env, jobject thisObj, jobjectArray jInputArray, jlong jResultMat, jboolean waveCorrect, jboolean multiBand)
{
	/* marshaling array of mats: http://stackoverflow.com/a/16111442 */

	jclass matClass = env->FindClass("org/opencv/core/Mat");
	jmethodID getNativeAddr = env->GetMethodID(matClass, "getNativeObjAddr", "()J");

	int numImgs = env->GetArrayLength(jInputArray);

	vector<Mat> natImgs;
	for(int i=0; i < numImgs; ++i) {
		natImgs.push_back(
			*(Mat*)env->CallLongMethod(
					env->GetObjectArrayElement(jInputArray, i),
					getNativeAddr
			)
		);
	}

	/* The core stitching calls: */
	Stitcher stitcher = Stitcher::createDefault();

	stitcher.setWaveCorrection((bool) waveCorrect);

	Ptr<detail::Blender> blender;

	if ((bool) multiBand) {
		blender = new detail::MultiBandBlender();
	} else {
		blender = new detail::FeatherBlender();
	}

	stitcher.setBlender(blender);

	Mat result;
	stitcher.stitch(natImgs, result);

	*(Mat*)jResultMat = result;

}
}
