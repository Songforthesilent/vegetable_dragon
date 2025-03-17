from flask import Flask, request, jsonify
import torch
from transformers import BertTokenizer, BertForSequenceClassification

app = Flask(__name__)

# 모델과 토크나이저 로드
MODEL_PATH = "model/clickbait_model.pt"
TOKENIZER_PATH = "tokenizer_dir"

tokenizer = BertTokenizer.from_pretrained(TOKENIZER_PATH)
model = BertForSequenceClassification.from_pretrained("bert-base-uncased", num_labels=2)
model.load_state_dict(torch.load(MODEL_PATH, map_location=torch.device('cpu')))
model.eval()

@app.route('/predict', methods=['POST'])
def predict():
    try:
        data = request.json
        text = data['text']

        # 텍스트 토큰화 및 변환
        inputs = tokenizer(text, return_tensors="pt", truncation=True, padding=True, max_length=512)
        with torch.no_grad():
            outputs = model(**inputs)

        prediction = torch.argmax(outputs.logits, dim=1).item()
        label = "clickbait" if prediction == 1 else "non-clickbait"

        return jsonify({'prediction': label})

    except Exception as e:
        return jsonify({"error": str(e)}), 400

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
