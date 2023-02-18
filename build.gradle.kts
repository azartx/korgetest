import com.soywiz.korge.gradle.*

plugins {
	alias(libs.plugins.korge)
}

korge {
	id = "com.korge.testapp.com.korge.testapp"
	supportExperimental3d()
	supportBox2d()
	supportDragonbones()
	supportSwf()
	targetAll()
}
