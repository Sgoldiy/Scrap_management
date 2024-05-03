package com.cpimca.scrapwala

class OnBoardingItems(
    val image: Int,
    val title: Int,
    val desc: Int
) {
    companion object{
        fun getData(): List<OnBoardingItems>{
            return listOf(
                OnBoardingItems(R.drawable.group1, R.string.onBoardingTitle1, R.string.onBoardingText1),
                OnBoardingItems(R.drawable.group2, R.string.onBoardingTitle2, R.string.onBoardingText2),
                OnBoardingItems(R.drawable.group3, R.string.onBoardingTitle3, R.string.onBoardingText3)
            )
        }
    }
}