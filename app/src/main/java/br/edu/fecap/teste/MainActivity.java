package br.edu.fecap.teste;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView imageViewTerrain;
    private EditText editTextDistance;
    private EditText editTextTime;
    private SeekBar seekBarDifficulty;
    private TextView textViewDifficulty;


    private List<Corrida> corridasCadastradas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageViewTerrain = findViewById(R.id.imageViewTerrain);
        editTextDistance = findViewById(R.id.editTextDistance);
        editTextTime = findViewById(R.id.editTextTime);
        seekBarDifficulty = findViewById(R.id.seekBarDifficulty);
        textViewDifficulty = findViewById(R.id.textViewDifficulty);

        RadioGroup radioGroupTerrain = findViewById(R.id.radioGroupTerrain);
        radioGroupTerrain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                switch (radioButton.getText().toString()) {
                    case "Asfalto":
                        imageViewTerrain.setImageResource(R.drawable.asfalto);
                        break;
                    case "Trilha":
                        imageViewTerrain.setImageResource(R.drawable.trilha);
                        break;
                    case "Pista":
                        imageViewTerrain.setImageResource(R.drawable.pista);
                        break;
                    case "Esteira":
                        imageViewTerrain.setImageResource(R.drawable.esteira);
                        break;
                    case "Outro":
                        break;
                }
            }
        });

        Switch switchOfficialRun = findViewById(R.id.switchOfficialRun);
        switchOfficialRun.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(MainActivity.this, "Corrida oficial ativada", Toast.LENGTH_SHORT).show();
                    showOfficialRunAlertDialog();
                } else {
                    Toast.makeText(MainActivity.this, "Corrida oficial desativada", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btnSave = findViewById(R.id.btnExibir);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String distancia = editTextDistance.getText().toString();
                String tempo = editTextTime.getText().toString();
                String terrenoSelecionado = getTerrenoSelecionado();
                int dificuldade = seekBarDifficulty.getProgress();


                corridasCadastradas.add(new Corrida(distancia, tempo, terrenoSelecionado, dificuldade));

                exibirCorridasCadastradas();
            }
        });

        Button btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextDistance.setText("");
                editTextTime.setText("");
            }
        });

        seekBarDifficulty.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateDifficultyText(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void updateDifficultyText(int progress) {
        String difficulty;
        if (progress < 4) {
            difficulty = "Fácil";
            textViewDifficulty.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        } else if (progress < 7) {
            difficulty = "Moderado";
            textViewDifficulty.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
        } else {
            difficulty = "Difícil";
            textViewDifficulty.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }
        textViewDifficulty.setText("Nível de Dificuldade: " + difficulty);
    }

    private void exibirCorridasCadastradas() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Corrida corrida : corridasCadastradas) {
            String dificuldade;
            int cor;


            if (corrida.getDificuldade() < 4) {
                dificuldade = "Fácil";
                cor = getResources().getColor(android.R.color.holo_green_dark);
            } else if (corrida.getDificuldade() < 7) {
                dificuldade = "Moderado";
                cor = getResources().getColor(android.R.color.holo_orange_dark);
            } else {
                dificuldade = "Difícil";
                cor = getResources().getColor(android.R.color.holo_red_dark);
            }


            stringBuilder.append("Distância: ").append(corrida.getDistancia())
                    .append(" km, Tempo: ").append(corrida.getTempo())
                    .append(" minutos, Terreno: ").append(corrida.getTerreno())
                    .append(", Dificuldade: ").append(dificuldade).append("\n");
            textViewDifficulty.setTextColor(cor);
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Corridas Cadastradas");
        builder.setMessage(stringBuilder.toString());
        builder.setPositiveButton("OK", null);
        builder.create().show();
    }

    private void showOfficialRunAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Salvar Corrida");
        builder.setMessage("Deseja salvar esta corrida?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Corrida salva com sucesso", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Corrida não salva", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }

    private String getTerrenoSelecionado() {
        RadioGroup radioGroupTerrain = findViewById(R.id.radioGroupTerrain);
        int radioButtonId = radioGroupTerrain.getCheckedRadioButtonId();
        if (radioButtonId != -1) {
            RadioButton radioButton = findViewById(radioButtonId);
            return radioButton.getText().toString();
        } else {
            return "Nenhum terreno selecionado";
        }
    }
}
