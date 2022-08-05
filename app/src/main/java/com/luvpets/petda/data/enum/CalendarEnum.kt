package com.luvpets.petda.data.enum

enum class CalendarFeed(type: String, status: String) {
  NONE("none", "안먹음"),
  LOW("low", "적게 먹음"),
  NORMAL("normal", "평소대로 먹음"),
  UP("up", "많이 먹음")
}

enum class CalendarWater(type: String, status: String) {
  NONE("none", "안마심"),
  LOW("low", "적게 마심"),
  NORMAL("normal", "평소대로 마심"),
  UP("up", "많이 마심")
}

enum class CalendarPooColor(type: String, status: String, color: String) {
  BROWN("brown", "갈색", "#7b6019"),
  GRAY("gray", "회색", "756f69"),
  YELLOW("yellow", "노란색", "d3b055"),
  ORANGE("orange", "주황색", "e2a051"),
  RED("red", "혈변", "ee6161"),
  PURPLE("purple", "보라색", "7655d3")
}

enum class CalendarPeeColor(type: String, status: String, color: String) {
  LIGHT("light", "투명색", "fff4b8"),
  YELLOW("yellow", "진노란색", "fadb6f"),
  ORANGE("orange", "진한 주황색", "f2982e"),
  GREEN("green", "초록색", "6fd986"),
  BROWN("brown", "진한 갈색", "8f6e60"),
  RED("red", "혈뇨", "ee6161")
}

enum class CalendarHealthy(type: String, status: String) {
  PILL("pill", "영양제"),
  THROW_UP("throwUp", "구토"),
  INOCULATION("inoculation", "접종"),
  HOSPITAL("hospital", "병원"),
  SICK("sick", "이상증상")
}

enum class CalendarBeauty(type: String, status: String) {
  TEETH("teeth", "양치"),
  WASH("wash", "목욕"),
  BRUSH("brush", "미용"),
  EAR("ear", "귀청소")
}