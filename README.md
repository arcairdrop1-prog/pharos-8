# Pharos Wallet Risk Verifier Agent

An intelligent compliance and security agent developed in **Java** for the Pharos Agent Arena designed to scan and flag suspicious wallet or contract histories prior to executing on-chain transactions[cite: 7].

## 📋 Requirements Met

1. **Multi-Signal Verification (Req. 01):** Analyzes at least 4 critical signals including contract age, code verification status, interactions with flagged addresses, unusual approvals, and fresh funding[cite: 7].
2. **Risk Level Categorization (Req. 02):** Assigns a definitive risk level (Low, Medium, or High) accompanied by specific reasoning[cite: 7].
3. **Plain Language Explanations (Req. 03):** Breaks down each identified flag into clear, understandable explanations[cite: 7].
4. **Demonstration Scenarios (Req. 04):** Includes built-in execution examples covering both a clean and a high-risk target[cite: 7].

## 🚀 How to Run

1. Certifique-se de ter o **Java JDK** instalado.
2. Compile o ficheiro:
   ```bash
   javac WalletRiskVerifier.java
