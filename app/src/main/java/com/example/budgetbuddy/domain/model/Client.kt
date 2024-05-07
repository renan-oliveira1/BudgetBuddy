package com.example.budgetbuddy.domain.model

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class Client(
    @PrimaryKey val clientId: String = GenerateIdHelper.generateRandomId(),
    @NonNull val name: String,
    @Nullable val cpf: String? = null,
    @NonNull val timestamp: Long
)


class InvalidClientException(message: String): Exception(message)