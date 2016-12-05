package com.wfe.core.input;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by Rick on 06.10.2016.
 */
public class Keys {

	public static final int
    KEY_UNKNOWN = -1,
    KEY_SPACE = 32,
    KEY_APOSTROPHE = 39, /* ' */
    KEY_COMMA = 44, /* , */
    KEY_MINUS = 45, /* - */
    KEY_PERIOD = 46, /* . */
    KEY_SLASH = 47, /* / */
    KEY_0 = 48,
    KEY_1 = 49,
    KEY_2 = 50,
    KEY_3 = 51,
    KEY_4 = 52,
    KEY_5 = 53,
    KEY_6 = 54,
    KEY_7 = 55,
    KEY_8 = 56,
    KEY_9 = 57,
    KEY_SEMICOLON = 59, /* ; */
    KEY_EQUAL = 61, /* = */
    KEY_A = 65,
    KEY_B = 66,
    KEY_C = 67,
    KEY_D = 68,
    KEY_E = 69,
    KEY_F = 70,
    KEY_G = 71,
    KEY_H = 72,
    KEY_I = 73,
    KEY_J = 74,
    KEY_K = 75,
    KEY_L = 76,
    KEY_M = 77,
    KEY_N = 78,
    KEY_O = 79,
    KEY_P = 80,
    KEY_Q = 81,
    KEY_R = 82,
    KEY_S = 83,
    KEY_T = 84,
    KEY_U = 85,
    KEY_V = 86,
    KEY_W = 87,
    KEY_X = 88,
    KEY_Y = 89,
    KEY_Z = 90,
    KEY_LEFT_BRACKET = 91, /* [ */
    KEY_BACKSLASH = 92, /* \ */
    KEY_RIGHT_BRACKET = 93, /* ] */
    KEY_GRAVE_ACCENT = 96, /* ` */
    KEY_WORLD_1 = 161, /* non-US #1 */
    KEY_WORLD_2 = 162, /* non-US #2 */
    KEY_ESCAPE = 256,
    KEY_ENTER = 257,
    KEY_TAB = 258,
    KEY_BACKSPACE = 259,
    KEY_INSERT = 260,
    KEY_DELETE = 261,
    KEY_RIGHT = 262,
    KEY_LEFT = 263,
    KEY_DOWN = 264,
    KEY_UP = 265,
    KEY_PAGE_UP = 266,
    KEY_PAGE_DOWN = 267,
    KEY_HOME = 268,
    KEY_END = 269,
    KEY_CAPS_LOCK = 280,
    KEY_SCROLL_LOCK = 281,
    KEY_NUM_LOCK = 282,
    KEY_PRINT_SCREEN = 283,
    KEY_PAUSE = 284,
    KEY_F1 = 290,
    KEY_F2 = 291,
    KEY_F3 = 292,
    KEY_F4 = 293,
    KEY_F5 = 294,
    KEY_F6 = 295,
    KEY_F7 = 296,
    KEY_F8 = 297,
    KEY_F9 = 298,
    KEY_F10 = 299,
    KEY_F11 = 300,
    KEY_F12 = 301,
    KEY_F13 = 302,
    KEY_F14 = 303,
    KEY_F15 = 304,
    KEY_F16 = 305,
    KEY_F17 = 306,
    KEY_F18 = 307,
    KEY_F19 = 308,
    KEY_F20 = 309,
    KEY_F21 = 310,
    KEY_F22 = 311,
    KEY_F23 = 312,
    KEY_F24 = 313,
    KEY_F25 = 314,
    KEY_KP_0 = 320,
    KEY_KP_1 = 321,
    KEY_KP_2 = 322,
    KEY_KP_3 = 323,
    KEY_KP_4 = 324,
    KEY_KP_5 = 325,
    KEY_KP_6 = 326,
    KEY_KP_7 = 327,
    KEY_KP_8 = 328,
    KEY_KP_9 = 329,
    KEY_KP_DECIMAL = 330,
    KEY_KP_DIVIDE = 331,
    KEY_KP_MULTIPLY = 332,
    KEY_KP_SUBTRACT = 333,
    KEY_KP_ADD = 334,
    KEY_KP_ENTER = 335,
    KEY_KP_EQUAL = 336,
    KEY_LEFT_SHIFT = 340,
    KEY_LEFT_CONTROL = 341,
    KEY_LEFT_ALT = 342,
    KEY_LEFT_SUPER = 343,
    KEY_RIGHT_SHIFT = 344,
    KEY_RIGHT_CONTROL = 345,
    KEY_RIGHT_ALT = 346,
    KEY_RIGHT_SUPER = 347,
    KEY_MENU = 348,
    KEY_LAST = GLFW_KEY_MENU;

	public static String toString(int keycode) {
	switch (keycode) {
	    case Keys.KEY_UNKNOWN:
	        return "Unknown";
	    case Keys.KEY_SPACE:
	        return "space";
	    case Keys.KEY_APOSTROPHE:
	        return "'";
	    case Keys.KEY_COMMA:
	        return ",";
	    case Keys.KEY_MINUS:
	        return "-";
	    case Keys.KEY_PERIOD:
	        return ".";
	    case Keys.KEY_SLASH:
	        return "/";
	    case Keys.KEY_0:
	        return "0";
	    case Keys.KEY_1:
	        return "1";
	    case Keys.KEY_2:
	        return "2";
	    case Keys.KEY_3:
	        return "3";
	    case Keys.KEY_4:
	        return "4";
	    case Keys.KEY_5:
	        return "5";
	    case Keys.KEY_6:
	        return "6";
	    case Keys.KEY_7:
	        return "7";
	    case Keys.KEY_8:
	        return "8";
	    case Keys.KEY_9:
	        return "9";
	    case Keys.KEY_SEMICOLON:
	        return ";";
	    case Keys.KEY_EQUAL:
	        return "=";
	    case Keys.KEY_A:
	        return "a";
	    case Keys.KEY_B:
	        return "b";
	    case Keys.KEY_C:
	        return "c";
	    case Keys.KEY_D:
	        return "d";
	    case Keys.KEY_E:
	        return "e";
	    case Keys.KEY_F:
	        return "f";
	    case Keys.KEY_G:
	        return "g";
	    case Keys.KEY_H:
	        return "h";
	    case Keys.KEY_I:
	        return "i";
	    case Keys.KEY_J:
	        return "j";
	    case Keys.KEY_K:
	        return "k";
	    case Keys.KEY_L:
	        return "l";
	    case Keys.KEY_M:
	        return "m";
	    case Keys.KEY_N:
	        return "n";
	    case Keys.KEY_O:
	        return "o";
	    case Keys.KEY_P:
	        return "p";
	    case Keys.KEY_Q:
	        return "q";
	    case Keys.KEY_R:
	        return "r";
	    case Keys.KEY_S:
	        return "s";
	    case Keys.KEY_T:
	        return "t";
	    case Keys.KEY_U:
	        return "u";
	    case Keys.KEY_V:
	        return "v";
	    case Keys.KEY_W:
	        return "w";
	    case Keys.KEY_X:
	        return "x";
	    case Keys.KEY_Y:
	        return "y";
	    case Keys.KEY_Z:
	        return "z";
	    case Keys.KEY_LEFT_BRACKET:
	        return "[";
	    case Keys.KEY_BACKSLASH:
	        return "\\";
	    case Keys.KEY_RIGHT_BRACKET:
	        return "]";
	    case Keys.KEY_GRAVE_ACCENT:
	        return "`";
	    case Keys.KEY_WORLD_1:
	        return "world 1";
	    case Keys.KEY_WORLD_2:
	        return "world 2";
	    case Keys.KEY_ESCAPE:
	        return "escape";
	    case Keys.KEY_ENTER:
	        return "enter";
	    case Keys.KEY_TAB:
	        return "tab";
	    case Keys.KEY_BACKSPACE:
	        return "backspace";
	    case Keys.KEY_INSERT:
	        return "insert";
	    case Keys.KEY_DELETE:
	        return "delete";
	    case Keys.KEY_RIGHT:
	        return "right";
	    case Keys.KEY_LEFT:
	        return "left";
	    case Keys.KEY_DOWN:
	        return "down";
	    case Keys.KEY_UP:
	        return "up";
	    case Keys.KEY_PAGE_UP:
	        return "page up";
	    case Keys.KEY_PAGE_DOWN:
	        return "page down";
	    case Keys.KEY_HOME:
	        return "home";
	    case Keys.KEY_END:
	        return "end";
	    case Keys.KEY_CAPS_LOCK:
	        return "caps lock";
	    case Keys.KEY_SCROLL_LOCK:
	        return "scroll lock";
	    case Keys.KEY_NUM_LOCK:
	        return "num lock";
	    case Keys.KEY_PRINT_SCREEN:
	        return "print screen";
	    case Keys.KEY_PAUSE:
	        return "pause";
	    case Keys.KEY_F1:
	        return "f1";
	    case Keys.KEY_F2:
	        return "f2";
	    case Keys.KEY_F3:
	        return "f3";
	    case Keys.KEY_F4:
	        return "f4";
	    case Keys.KEY_F5:
	        return "f5";
	    case Keys.KEY_F6:
	        return "f6";
	    case Keys.KEY_F7:
	        return "f7";
	    case Keys.KEY_F8:
	        return "f8";
	    case Keys.KEY_F9:
	        return "f9";
	    case Keys.KEY_F10:
	        return "f10";
	    case Keys.KEY_F11:
	        return "f11";
	    case Keys.KEY_F12:
	        return "f12";
	    case Keys.KEY_F13:
	        return "f13";
	    case Keys.KEY_F14:
	        return "f14";
	    case Keys.KEY_F15:
	        return "f15";
	    case Keys.KEY_F16:
	        return "f16";
	    case Keys.KEY_F17:
	        return "f17";
	    case Keys.KEY_F18:
	        return "f18";
	    case Keys.KEY_F19:
	        return "f19";
	    case Keys.KEY_F20:
	        return "f20";
	    case Keys.KEY_F21:
	        return "f21";
	    case Keys.KEY_F22:
	        return "f22";
	    case Keys.KEY_F23:
	        return "f23";
	    case Keys.KEY_F24:
	        return "f24";
	    case Keys.KEY_F25:
	        return "f25";
	    case Keys.KEY_KP_0:
	        return "num 0";
	    case Keys.KEY_KP_1:
	        return "num 1";
	    case Keys.KEY_KP_2:
	        return "num 2";
	    case Keys.KEY_KP_3:
	        return "num 3";
	    case Keys.KEY_KP_4:
	        return "num 4";
	    case Keys.KEY_KP_5:
	        return "num 5";
	    case Keys.KEY_KP_6:
	        return "num 6";
	    case Keys.KEY_KP_7:
	        return "num 7";
	    case Keys.KEY_KP_8:
	        return "num 8";
	    case Keys.KEY_KP_9:
	        return "num 9";
	    case Keys.KEY_KP_DECIMAL:
	        return "num decimal";
	    case Keys.KEY_KP_DIVIDE:
	        return "num divide";
	    case Keys.KEY_KP_MULTIPLY:
	        return "num multiply";
	    case Keys.KEY_KP_SUBTRACT:
	        return "num subtract";
	    case Keys.KEY_KP_ADD:
	        return "num add";
	    case Keys.KEY_KP_ENTER:
	        return "num enter";
	    case Keys.KEY_KP_EQUAL:
	        return "num equal";
	    case Keys.KEY_LEFT_SHIFT:
	        return "left shift";
	    case Keys.KEY_LEFT_CONTROL:
	        return "left control";
	    case Keys.KEY_LEFT_ALT:
	        return "left alt";
	    case Keys.KEY_LEFT_SUPER:
	        return "left super";
	    case Keys.KEY_RIGHT_SHIFT:
	        return "right shift";
	    case Keys.KEY_RIGHT_CONTROL:
	        return "right control";
	    case Keys.KEY_RIGHT_ALT:
	        return "right alt";
	    case Keys.KEY_RIGHT_SUPER:
	        return "right super";
	    case Keys.KEY_MENU:
	        return "menu";
	    default:
	        return null;
		}
	}

    public static int[] MODIFIERS = {
            KEY_LEFT_SHIFT,
            KEY_RIGHT_SHIFT,
            KEY_LEFT_ALT,
            KEY_RIGHT_ALT,
            KEY_LEFT_CONTROL,
            KEY_RIGHT_CONTROL,
            KEY_LEFT_SUPER,
            KEY_RIGHT_SUPER
    };

}
