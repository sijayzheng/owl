package cn.sijay.owl.common.utils;

import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.*;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.ECGenParameterSpec;

/**
 * 国密算法工具类 (SM2 非对称加密 / SM4 对称加密)
 * <p>
 * 密钥格式约定：
 * - SM2 公私钥：十六进制字符串，公钥不带 0x04 前缀
 * - SM4 密钥/IV：十六进制字符串，长度均为 32 个字符 (16 字节)
 *
 * @author sijay
 * @since 2026-04-14
 */
public final class SmUtil {

    static {
        // 注册 BouncyCastle 安全提供者
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    // ---------- SM2 相关常量 ----------
    private static final String SM2_CURVE_NAME = "sm2p256v1";
    private static final ECNamedCurveParameterSpec EC_SPEC =
        ECNamedCurveTable.getParameterSpec(SM2_CURVE_NAME);
    private static final ECDomainParameters DOMAIN_PARAMS = new ECDomainParameters(
        EC_SPEC.getCurve(),
        EC_SPEC.getG(),
        EC_SPEC.getN(),
        EC_SPEC.getH()
    );

    // ---------- SM4 相关常量 ----------
    private static final String SM4_ALGORITHM = "SM4";
    private static final String SM4_CBC_PADDING = "SM4/CBC/PKCS5Padding";
    private static final int SM4_KEY_SIZE = 16; // 128 bits

    // ---------- SM2 密钥生成 ----------

    /**
     * 生成 SM2 密钥对
     *
     * @return KeyPair (PublicKey 为 BCECPublicKey, PrivateKey 为 BCECPrivateKey)
     */
    public static KeyPair genSm2KeyPair() throws NoSuchAlgorithmException,
        NoSuchProviderException, InvalidAlgorithmParameterException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC", BouncyCastleProvider.PROVIDER_NAME);
        kpg.initialize(new ECGenParameterSpec(SM2_CURVE_NAME), new SecureRandom());
        return kpg.generateKeyPair();
    }

    /**
     * 获取公钥十六进制字符串（不含 0x04 前缀）
     */
    public static String getSm2PublicKeyHex(BCECPublicKey publicKey) {
        byte[] encoded = publicKey.getQ().getEncoded(false);
        // 去掉第一个字节 0x04，只保留坐标数据 (64 字节)
        return Hex.toHexString(encoded, 1, encoded.length - 1);
    }

    // ---------- SM2 加密/解密 ----------

    /**
     * SM2 加密
     *
     * @param plainText    明文字符串
     * @param publicKeyHex 公钥十六进制字符串 (不带 04 前缀，长度 128 字符)
     * @return 密文十六进制字符串 (C1C3C2 格式)
     */
    public static String sm2Encrypt(String plainText, String publicKeyHex) throws Exception {
        validateHexString(publicKeyHex, 128, "SM2 公钥");
        byte[] plainBytes = plainText.getBytes(StandardCharsets.UTF_8);

        // 解码公钥点
        byte[] pubKeyBytes = decodePublicKeyBytes(publicKeyHex);
        ECPoint pubPoint = DOMAIN_PARAMS.getCurve().decodePoint(pubKeyBytes);
        ECPublicKeyParameters pubKeyParams = new ECPublicKeyParameters(pubPoint, DOMAIN_PARAMS);

        SM2Engine engine = new SM2Engine(SM2Engine.Mode.C1C3C2);
        engine.init(true, new ParametersWithRandom(pubKeyParams, new SecureRandom()));

        byte[] cipherData = engine.processBlock(plainBytes, 0, plainBytes.length);
        return Hex.toHexString(cipherData);
    }

    /**
     * SM2 解密
     *
     * @param cipherHex     密文十六进制字符串
     * @param privateKeyHex 私钥十六进制字符串 (长度 64 字符)
     * @return 解密后的明文字符串
     */
    public static String sm2Decrypt(String cipherHex, String privateKeyHex) throws Exception {
        validateHexString(privateKeyHex, 64, "SM2 私钥");
        byte[] cipherData = Hex.decode(cipherHex);

        BigInteger privateKeyVal = new BigInteger(1, Hex.decode(privateKeyHex));
        ECPrivateKeyParameters privKeyParams = new ECPrivateKeyParameters(privateKeyVal, DOMAIN_PARAMS);

        SM2Engine engine = new SM2Engine(SM2Engine.Mode.C1C3C2);
        engine.init(false, privKeyParams);

        byte[] decryptedData = engine.processBlock(cipherData, 0, cipherData.length);
        return new String(decryptedData, StandardCharsets.UTF_8);
    }

    // ---------- SM3 哈希 ----------

    /**
     * 对字节数组进行 SM3 哈希运算
     *
     * @param data 原始数据
     * @return 32字节的哈希值
     */
    public static String sm3Hash(byte[] data) {
        SM3Digest digest = new SM3Digest();
        digest.update(data, 0, data.length);
        byte[] hash = new byte[digest.getDigestSize()];
        digest.doFinal(hash, 0);
        return Hex.toHexString(hash);
    }

    /**
     * 对字符串进行 SM3 哈希运算（UTF-8 编码）
     *
     * @param data 原始字符串
     * @return 32字节的哈希值
     */
    public static String sm3Hash(String data) {
        return sm3Hash(data.getBytes(java.nio.charset.StandardCharsets.UTF_8));
    }

    /**
     * 计算 SM3-HMAC 并返回十六进制字符串
     */
    public static String sm3Hmac(String key, String data) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        KeyParameter keyParameter = new KeyParameter(keyBytes);
        SM3Digest digest = new SM3Digest();
        HMac mac = new HMac(digest);
        mac.init(keyParameter);
        mac.update(dataBytes, 0, dataBytes.length);
        byte[] result = new byte[mac.getMacSize()];
        mac.doFinal(result, 0);
        return Hex.toHexString(result);
    }

    // ---------- SM4 加密/解密 (CBC 模式，PKCS5Padding) ----------

    /**
     * SM4 加密 (CBC 模式)
     *
     * @param plainText 明文字符串
     * @param keyHex    密钥十六进制字符串 (长度 32 字符，对应 16 字节)
     * @param ivHex     初始向量十六进制字符串 (长度 32 字符，对应 16 字节)
     * @return Base64 编码的密文
     */
    public static String sm4Encrypt(String plainText, String keyHex, String ivHex) throws Exception {
        validateHexString(keyHex, SM4_KEY_SIZE * 2, "SM4 密钥");
        validateHexString(ivHex, SM4_KEY_SIZE * 2, "SM4 IV");

        byte[] keyBytes = Hex.decode(keyHex);
        byte[] ivBytes = Hex.decode(ivHex);
        byte[] plainBytes = plainText.getBytes(StandardCharsets.UTF_8);

        Cipher cipher = Cipher.getInstance(SM4_CBC_PADDING, BouncyCastleProvider.PROVIDER_NAME);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, SM4_ALGORITHM);
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

        byte[] encrypted = cipher.doFinal(plainBytes);
        return Base64.toBase64String(encrypted);
    }

    /**
     * SM4 解密 (CBC 模式)
     *
     * @param cipherBase64 Base64 编码的密文
     * @param keyHex       密钥十六进制字符串 (长度 32 字符)
     * @param ivHex        初始向量十六进制字符串 (长度 32 字符)
     * @return 解密后的明文字符串
     */
    public static String sm4Decrypt(String cipherBase64, String keyHex, String ivHex) throws Exception {
        validateHexString(keyHex, SM4_KEY_SIZE * 2, "SM4 密钥");
        validateHexString(ivHex, SM4_KEY_SIZE * 2, "SM4 IV");

        byte[] keyBytes = Hex.decode(keyHex);
        byte[] ivBytes = Hex.decode(ivHex);
        byte[] cipherBytes = Base64.decode(cipherBase64);

        Cipher cipher = Cipher.getInstance(SM4_CBC_PADDING, BouncyCastleProvider.PROVIDER_NAME);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, SM4_ALGORITHM);
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        byte[] decrypted = cipher.doFinal(cipherBytes);
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    // ---------- 私有辅助方法 ----------

    /**
     * 将公钥十六进制字符串转换为完整的公钥字节数组（添加 0x04 前缀）
     */
    private static byte[] decodePublicKeyBytes(String publicKeyHex) {
        // 公钥坐标点占 64 字节 (X 32 + Y 32)
        byte[] coord = Hex.decode(publicKeyHex);
        if (coord.length != 64) {
            throw new IllegalArgumentException("SM2 公钥坐标必须为 64 字节");
        }
        byte[] full = new byte[65];
        full[0] = 0x04; // 非压缩标识
        System.arraycopy(coord, 0, full, 1, 64);
        return full;
    }

    /**
     * 校验十六进制字符串长度是否符合预期
     *
     * @param hexStr      待校验的字符串
     * @param expectedLen 期望的字符长度 (通常为字节数的 2 倍)
     * @param paramName   参数名称 (用于异常信息)
     */
    private static void validateHexString(String hexStr, int expectedLen, String paramName) {
        if (hexStr == null || hexStr.length() != expectedLen) {
            throw new IllegalArgumentException(
                String.format("%s 必须为 %d 位十六进制字符串", paramName, expectedLen));
        }
        // 简单校验是否为合法十六进制字符（可选）
        if (!hexStr.matches("^[0-9a-fA-F]+$")) {
            throw new IllegalArgumentException(paramName + " 包含非法十六进制字符");
        }
    }

    // 禁止实例化
    private SmUtil() {
    }
}
